package com.example.mealmate.modules.meal_plan.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.modules.meal_plan.data.model.MealPlanModel
import com.example.mealmate.modules.meal_plan.data.repo.MealPlanRepo
import com.example.mealmate.shared.DialogController
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import com.example.mealmate.shared.model.DialogType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealPlanViewModel @Inject constructor(
    private val repo: MealPlanRepo,
    private val sessionManager: SessionManager,
    private val tokenManager: TokenManager,
    private val dialogController: DialogController
) : ViewModel() {
    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> get() = _userName

    private val _showDialog = MutableStateFlow<Boolean>(false)
    val showDialog: StateFlow<Boolean> get() = _showDialog

    fun updateShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    private val _mealList = MutableStateFlow<List<MealPlanModel>>(emptyList())
    val mealList: StateFlow<List<MealPlanModel>> get() = _mealList

    private val _mealListError = MutableStateFlow<String>("")
    val mealListError: StateFlow<String> get() = _mealListError

    private val _isMealListLoading = MutableStateFlow<Boolean>(false)
    val isMealListLoading: StateFlow<Boolean> get() = _isMealListLoading

    private val _showLoadingDialog = MutableStateFlow<Boolean>(false)
    val showLoadingDialog: StateFlow<Boolean> get() = _showLoadingDialog

    private val _isPageRefreshing = MutableStateFlow<Boolean>(false)
    val isPageRefreshing: StateFlow<Boolean> get() = _isPageRefreshing

    init {
        _userName.value = sessionManager.me?.name
        getMealPlanList()
    }

    fun pageRefresh() {
        _isPageRefreshing.value = true
        _mealListError.value = ""
        getMealPlanList()
        _isPageRefreshing.value = false
    }

    fun showCardRemoveDialog(meal: MealPlanModel) {
        dialogController.showDialog(
            type = DialogType.WARNING,
            title = "Remove meal form your plan?",
            message = "Are you sure?",
            onConfirm = { removeMealPlan(meal) },
            onDismiss = {}
        )
    }

    private fun removeMealPlan(meal: MealPlanModel) {
        _showLoadingDialog.value = true
        viewModelScope.launch {
            val result = repo.deleteMealPlan(mealId = meal.id)
            result.fold(
                onLeft = { failure ->
                    _showLoadingDialog.value = false
                    dialogController.showDialog(
                        type = DialogType.ERROR,
                        title = "Error",
                        message = failure.errorMessage,
                        onConfirm = { },
                        onDismiss = { }
                    )
                },
                onRight = { message ->
                    _showLoadingDialog.value = false
                    getMealPlanList()
                }
            )
        }
    }

    private fun getMealPlanList() {
        _isMealListLoading.value = true
        _mealListError.value = ""
        viewModelScope.launch {
            val result = repo.getMealPlan()
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

    fun updateMealPlan(info: MealPlanModel) {
        _showLoadingDialog.value = true
        viewModelScope.launch {
            val result = repo.updateMealPlan(
                info = info
            )
            result.fold(
                onLeft = { failure ->
                    _showLoadingDialog.value = false
                    dialogController.showDialog(
                        type = DialogType.ERROR,
                        title = "Error",
                        message = failure.errorMessage,
                        onConfirm = { },
                        onDismiss = { }
                    )
                },
                onRight = { message ->
                    _showLoadingDialog.value = false
                    getMealPlanList()
                    dialogController.showDialog(
                        type = DialogType.SUCCESS,
                        title = "Success",
                        message = message,
                        onConfirm = { },
                        onDismiss = { }
                    )
                }
            )
        }
    }

}