package com.example.mealmate.navigation.graphes

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mealmate.modules.auth.ui.screen.LoginPage
import com.example.mealmate.modules.auth.ui.screen.OTPPage
import com.example.mealmate.modules.auth.ui.screen.RegisterDonePage
import com.example.mealmate.modules.auth.ui.screen.SignUpPage
import com.example.mealmate.modules.auth.ui.viewmodel.LoginViewmodel
import com.example.mealmate.modules.auth.ui.viewmodel.RegisterViewModel
import com.example.mealmate.navigation.Graph
import com.example.mealmate.navigation.Screen
import sharedViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(route = Graph.AuthGraph.route, startDestination = Screen.SignupScreen.route) {
        composable(
            Screen.SignupScreen.route,
            enterTransition = {
                slideInVertically(animationSpec = tween(300), initialOffsetY = { it })
            },
            exitTransition = {
                slideOutVertically(animationSpec = tween(500), targetOffsetY = { it })
            },
        ) {
            val registerViewModel = it.sharedViewModel<RegisterViewModel>(navController)
            SignUpPage(
                appNavi = navController,
                registerViewModel = registerViewModel
            )
        }

        composable(
            route = Screen.OtpScreen.route
        ) {
            val registerViewModel = it.sharedViewModel<RegisterViewModel>(navController)
            OTPPage(
                registerViewModel = registerViewModel,
                appNavi = navController,
            )
        }

        composable(Screen.LoginScreen.route) {
            val loginViewmodel = it.sharedViewModel<LoginViewmodel>(navController)
            LoginPage(navController, loginViewmodel)
        }
        composable(Screen.RegisterDoneScreen.route) { RegisterDonePage(navController) }
    }
}

enum class OtpSource { REGISTER, LOGIN }