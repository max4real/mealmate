package com.example.mealmate.modules.shopping_list.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.modules.shopping_list.data.model.ShoppingList
import com.example.mealmate.modules.shopping_list.data.model.ShoppingListIngredient
import com.example.mealmate.modules.shopping_list.data.repo.ShoppingListRepo
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repo: ShoppingListRepo,
    private val sessionManager: SessionManager,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> get() = _userName

    private val _showDialog = MutableStateFlow<Boolean>(false)
    val showDialog: StateFlow<Boolean> get() = _showDialog

    fun updateShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    private val _shoppingList = MutableStateFlow<List<ShoppingList>>(emptyList())
    val shoppingList: StateFlow<List<ShoppingList>> get() = _shoppingList

    private val _shoppingListError = MutableStateFlow<String>("")
    val shoppingListError: StateFlow<String> get() = _shoppingListError

    private val _isShoppingListLoading = MutableStateFlow<Boolean>(false)
    val isShoppingListLoading: StateFlow<Boolean> get() = _isShoppingListLoading

    private val _showLoadingDialog = MutableStateFlow<Boolean>(false)
    val showLoadingDialog: StateFlow<Boolean> get() = _showLoadingDialog

    private val _isPageRefreshing = MutableStateFlow<Boolean>(false)
    val isPageRefreshing: StateFlow<Boolean> get() = _isPageRefreshing

    private var debounceJob: Job? = null

    init {
        _userName.value = sessionManager.me?.name
        getShoppingList()
    }

    fun pageRefresh() {
        _isPageRefreshing.value = true
        _shoppingListError.value = ""
        getShoppingList()
        _isPageRefreshing.value = false
    }

    private fun getShoppingList() {
        _isShoppingListLoading.value = true
        _shoppingListError.value = ""
        viewModelScope.launch {
            val result = repo.getShoppingList()
            result.fold(onLeft = { failure ->
                _showLoadingDialog.value = false
                _shoppingListError.value = failure.errorMessage
            }, onRight = { dataList ->
                _shoppingList.value = dataList
                _isShoppingListLoading.value = false
                restoreCheckedItems()
            })
        }
    }

    private fun restoreCheckedItems() {
        val checkedItems = tokenManager.getCheckedIngredients()
        if (checkedItems.isNotEmpty()) {
            _shoppingList.value = _shoppingList.value.map { list ->
                list.copy(ingredients = list.ingredients.map { ingredient ->
                    val uniqueKey = "${list.id}-${ingredient.name}"
                    checkedItems[uniqueKey]?.let { checkedIngredient ->
                        ingredient.copy(bought = checkedIngredient.bought)
                    } ?: ingredient
                })
            }
            if (checkedItems.any()) {
                startDebounceTimer()
            }
        }
    }


    fun onIngredientChecked(
        shoppingListId: String, ingredient: ShoppingListIngredient, isChecked: Boolean
    ) {
        viewModelScope.launch {
            _shoppingList.value = _shoppingList.value.map { list ->
                if (list.id == shoppingListId) {
                    list.copy(ingredients = list.ingredients.map {
                        if (it.name == ingredient.name) it.copy(bought = isChecked) else it
                    })
                } else {
                    list
                }
            }
        }

        val currentChecked = tokenManager.getCheckedIngredients().toMutableMap()
        val uniqueKey = "$shoppingListId-${ingredient.name}"

        currentChecked[uniqueKey] = ingredient.copy(bought = isChecked)
        tokenManager.saveCheckedIngredients(currentChecked)

        startDebounceTimer()
    }

    private fun startDebounceTimer() {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(3000) // Using 3s for testing
            val checkedItems = tokenManager.getCheckedIngredients()
            if (checkedItems.isNotEmpty()) {
                val groupedIngredients = checkedItems.entries.groupBy(keySelector = {
                    it.key.substringBeforeLast('-')
                }, valueTransform = {
                    it.value
                })

                sendCheckedIngredientsToBackend(groupedIngredients)
            }
        }
    }

    private suspend fun sendCheckedIngredientsToBackend(groupedIngredients: Map<String, List<ShoppingListIngredient>>) {
        _showLoadingDialog.value = true

        val ingredientsForApi = mutableListOf<TestModel>()

        for ((listId, ingredients) in groupedIngredients) {
            for (ingredient in ingredients) {
                val apiModel = TestModel(
                    id = listId,
                    name = ingredient.name,
                    qty = ingredient.qty,
                    bought = ingredient.bought
                )
                ingredientsForApi.add(apiModel)
            }
        }
        val payload = TestListModel(ingredients = ingredientsForApi)

        val result = repo.updateCheckedIngredients(payload)
        result.fold(onLeft = { failure ->
            _shoppingListError.value = failure.errorMessage
        }, onRight = {
            println("API call successful!")
            tokenManager.clearCheckedIngredients()
        })

        _showLoadingDialog.value = false
    }

    override fun onCleared() {
        super.onCleared()
        debounceJob?.cancel()
        viewModelScope.launch {
            val checkedItems = tokenManager.getCheckedIngredients()
            if (checkedItems.isNotEmpty()) {
                val groupedIngredients =
                    checkedItems.entries.groupBy(
                        keySelector = { it.key.substringBeforeLast('-') },
                        valueTransform = { it.value })
                sendCheckedIngredientsToBackend(groupedIngredients)
                tokenManager.clearCheckedIngredients()
            }
        }
    }
}

data class TestListModel(
    val ingredients: List<TestModel>
)

data class TestModel(
    val id: String, val name: String, val qty: String, val bought: Boolean
)
