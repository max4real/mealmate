package com.example.mealmate.modules.meal_plan.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.example.mealmate.modules.meal_plan.data.repo.MealPlanRepo
import com.example.mealmate.shared.DialogController
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import com.example.mealmate.shared.model.DialogType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    init {
        _userName.value = sessionManager.me?.name
    }

    fun showCardRemoveDialog() {
        dialogController.showDialog(
            type = DialogType.WARNING,
            title = "Confirm",
            message = "Are you sure?",
            onConfirm = { /* ... */ },
            onDismiss = {}
        )
    }

//    private val _email = MutableStateFlow<String>("")
//    val email: StateFlow<String> = _email
//
//    fun setEmail(email: String) {
//        _email.value = email
//    }
}