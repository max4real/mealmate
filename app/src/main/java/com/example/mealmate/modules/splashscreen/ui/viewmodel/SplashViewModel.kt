package com.example.mealmate.modules.splashscreen.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mealmate.modules.auth.data.model.MeResponse
import com.example.mealmate.modules.auth.data.repo.LoginRepo
import com.example.mealmate.navigation.Graph
import com.example.mealmate.navigation.Screen
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import offAll
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val sessionManager: SessionManager,
    private val loginRepo: LoginRepo,
) : ViewModel() {

    fun checkLoginAndNavigate(appNavi: NavHostController) {
        viewModelScope.launch {

            if (tokenManager.getToken() != null) {
                getMe(
                    onError = { errorMsg ->
                        println(errorMsg)
                        appNavi.offAll(Graph.AuthGraph.route)
                    },
                    success = { meModel ->
                        sessionManager.me = meModel
                        appNavi.offAll(Screen.HomeScreen.route)
                    }
                )
            } else {
                appNavi.offAll(Graph.AuthGraph.route)
            }
        }
    }

    private fun getMe(
        success: (meResponse: MeResponse) -> Unit,
        onError: (errMsg: String) -> Unit
    ) {
        viewModelScope.launch {
            val result = loginRepo.getMe()
            result.fold(
                onLeft = { failure ->
                    onError(failure.errorMessage)
                },
                onRight = { me ->
                    success(me)
                }
            )
        }
    }
}


