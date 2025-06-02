package com.example.mealmate.modules.splashscreen.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mealmate.navigation.Graph
import com.example.mealmate.navigation.Screen
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import offAll
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val sessionManager: SessionManager
) : ViewModel() {

    fun checkLoginAndNavigate(appNavi: NavHostController) {
        viewModelScope.launch {
            delay(2000)
            if (tokenManager.getToken() != null) {
                appNavi.offAll(Screen.HomeScreen.route)
            } else {
                appNavi.offAll(Graph.AuthGraph.route)
            }
        }
    }
}


