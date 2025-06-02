package com.example.mealmate.navigation

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash")
    data object HomeScreen : Screen("home")
    data object LoginScreen : Screen("login")
    data object SignupScreen : Screen("auth")
    data object OtpScreen : Screen("otp-screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}

sealed class Graph(val route: String) {
    data object AuthGraph : Graph("auth-graph")
}
