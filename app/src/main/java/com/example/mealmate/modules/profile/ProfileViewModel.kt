package com.example.mealmate.modules.profile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.navigation.Screen
import com.example.mealmate.shared.DialogController
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import com.example.mealmate.shared.model.DialogType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import offAll
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val tokenManager: TokenManager,
    private val dialogController: DialogController
) : ViewModel() {
    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> get() = _userName

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
}



