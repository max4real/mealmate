package com.example.mealmate.modules.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mealmate.modules.profile.data.repo.ProfileRepo
import com.example.mealmate.navigation.Screen
import com.example.mealmate.shared.DialogController
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import com.example.mealmate.shared.model.DialogType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import offAll
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileRepo,
    private val sessionManager: SessionManager,
    private val tokenManager: TokenManager,
    private val dialogController: DialogController
) : ViewModel() {
    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> get() = _userName

    private val _showLoadingDialog = MutableStateFlow<Boolean>(false)
    val showLoadingDialog: StateFlow<Boolean> get() = _showLoadingDialog

    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> get() = _email


    init {
        _userName.value = sessionManager.me?.name
        _email.value = sessionManager.me?.email
    }

    fun logout(appNavi: NavHostController) {
        dialogController.showDialog(
            type = DialogType.WARNING,
            title = "Confirm",
            message = "Are you sure, you want to logout?",
            onConfirm = {
                tokenManager.clearToken()
                tokenManager.clearCheckedIngredients()
                appNavi.offAll(Screen.SplashScreen.route)
            },
            onDismiss = {}
        )

    }

    fun deleteAcc(appNavi: NavHostController) {
        dialogController.showDialog(
            type = DialogType.WARNING,
            title = "Confirm",
            message = "Are you sure, you want to delete your account? This action cannot be undone.",
            onConfirm = {
                deleteApi(appNavi = appNavi)
            },
            onDismiss = {}
        )
    }

    private fun deleteApi(appNavi: NavHostController) {
        _showLoadingDialog.value = true
        viewModelScope.launch {
            val result = repo.deleteAcc()
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
                    tokenManager.clearToken()
                    tokenManager.clearCheckedIngredients()
                    appNavi.offAll(Screen.SplashScreen.route)
                }
            )
        }
    }
}



