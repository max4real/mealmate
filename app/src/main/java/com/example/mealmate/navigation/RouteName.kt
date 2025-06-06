package com.example.mealmate.navigation

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash")
    data object LoginScreen : Screen("login")
    data object SignupScreen : Screen("auth")
    data object OtpScreen : Screen("otp-screen")
    data object RegisterDoneScreen : Screen("register-done")

    data object HomeScreen : Screen("home")
    data object MealPlan : Screen("meal-plan")
    data object ShoppingList : Screen("shopping-list")
    data object Profile : Screen("profile")

    data object NewMeal : Screen("new-meal")


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
