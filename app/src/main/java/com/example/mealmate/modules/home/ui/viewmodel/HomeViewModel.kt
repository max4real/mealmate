package com.example.mealmate.modules.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mealmate.modules.auth.data.model.LoginRequest
import com.example.mealmate.modules.auth.data.repo.LoginRepo
import com.example.mealmate.modules.home.data.repo.HomeRepo
import com.example.mealmate.navigation.Screen
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import to
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo,
    private val sessionManager: SessionManager,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> = _email

    fun setEmail(email: String) {
        _email.value = email
    }

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password

    fun setPassword(password: String) {
        _password.value = password
    }

    private val _loginErrorMessage = MutableStateFlow<String>("")
    val loginErrorMessage: StateFlow<String> = _loginErrorMessage


    private val _isLoginLoading = MutableStateFlow<Boolean>(false)
    val isLoginLoading: StateFlow<Boolean> = _isLoginLoading

//    fun onClickLoginBtn(appNavi: NavHostController) {
//        _isLoginLoading.value = true
//
//        login(
//            loginRequest = LoginRequest(
//                email = email.value,
//                password = password.value
//            ),
//            onError = { errorMsg ->
//                _isLoginLoading.value = false
//                _loginErrorMessage.value = errorMsg
//            },
//            success = { token ->
//                _isLoginLoading.value = false
//                println("Login Success - token: $token")
//                sessionManager.token = token
//                tokenManager.saveToken(token)
//                appNavi.to(Screen.HomeScreen.route)
//            }
//        )
//    }
//
//    private fun login(
//        loginRequest: LoginRequest,
//        success: (token: String) -> Unit,
//        onError: (errMsg: String) -> Unit
//    ) {
//        viewModelScope.launch {
//            val result = loginRepo.login(loginRequest)
//            result.fold(
//                onLeft = { failure ->
//                    onError(failure.errorMessage)
//                },
//                onRight = { token ->
//                    success(token)
//                }
//            )
//        }
//    }

}