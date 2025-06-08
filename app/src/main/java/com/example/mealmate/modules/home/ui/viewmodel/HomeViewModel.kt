package com.example.mealmate.modules.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.modules.home.data.model.CategoryModel
import com.example.mealmate.modules.home.data.model.MealDetailModel
import com.example.mealmate.modules.home.data.repo.HomeRepo
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo,
    private val sessionManager: SessionManager,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _showDialog = MutableStateFlow<Boolean>(false)
    val showDialog: StateFlow<Boolean> get() = _showDialog

    fun updateShowDialog(value: Boolean) {
        _showDialog.value = value
        _addToPlanError.value = ""
    }

    private val _isPageRefreshing = MutableStateFlow<Boolean>(false)
    val isPageRefreshing: StateFlow<Boolean> get() = _isPageRefreshing

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> get() = _userName

    private val _categoryList = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categoryList: StateFlow<List<CategoryModel>> get() = _categoryList

    private val _isCategoryLoading = MutableStateFlow<Boolean>(false)
    val isCategoryLoading: StateFlow<Boolean> get() = _isCategoryLoading

    private val _categoryError = MutableStateFlow<String>("")
    val categoryError: StateFlow<String> get() = _categoryError

    private val _mealList = MutableStateFlow<List<MealDetailModel>>(emptyList())
    val mealList: StateFlow<List<MealDetailModel>> get() = _mealList

    private val _mealListError = MutableStateFlow<String>("")
    val mealListError: StateFlow<String> get() = _mealListError

    private val _isMealListLoading = MutableStateFlow<Boolean>(false)
    val isMealListLoading: StateFlow<Boolean> get() = _isMealListLoading

    private val _addToPlanError = MutableStateFlow<String>("")
    val addToPlanError: StateFlow<String> get() = _addToPlanError

    private val _isAddToPlanLoading = MutableStateFlow<Boolean>(false)
    val isAddToPlanLoading: StateFlow<Boolean> get() = _isAddToPlanLoading

    init {
        _userName.value = sessionManager.me?.name
        getCategoryList()
        getMealListWithCategory(id = null)
    }

    fun pageRefresh() {
        _isPageRefreshing.value = true
        _categoryError.value = ""
        _mealListError.value = ""
        _addToPlanError.value = ""
        getCategoryList()
        getMealListWithCategory(id = null)
        _isPageRefreshing.value = false
    }

    private fun getCategoryList() {
        _isCategoryLoading.value = true
        viewModelScope.launch {
            val result = homeRepo.getCategoryList()
            result.fold(
                onLeft = { failure ->
                    // Handle error if needed
                    // Log.e("HomeViewModel", "Failed to fetch categories: ${failure.errorMessage}")
                    _categoryError.value = failure.errorMessage
                },
                onRight = { dataList ->
                    _categoryList.value = dataList
                    _isCategoryLoading.value = false
                }
            )
        }
    }

    fun getMealListWithCategory(id: String?) {
        _isMealListLoading.value = true
        _mealListError.value = ""
        viewModelScope.launch {
            val result = homeRepo.getMealList(categoryId = id)
            result.fold(
                onLeft = { failure ->
                    _isMealListLoading.value = false
                    _mealListError.value = failure.errorMessage
                },
                onRight = { dataList ->
                    _mealList.value = dataList
                    _isMealListLoading.value = false
                }
            )
        }
    }

    fun addToPlan(info: MealDetailModel) {
        _isAddToPlanLoading.value = true
        _addToPlanError.value = ""
        viewModelScope.launch {
            val result = homeRepo.addToPlan(
                request = info.toAddToPlanRequest()
            )
            result.fold(
                onLeft = { failure ->
                    _isAddToPlanLoading.value = false
                    _addToPlanError.value = failure.errorMessage
                },
                onRight = { data ->
//                    _showDialog.value = false
                    _addToPlanError.value = "Successfully added to plan."
                    _isAddToPlanLoading.value = false
                }
            )
        }
    }
}