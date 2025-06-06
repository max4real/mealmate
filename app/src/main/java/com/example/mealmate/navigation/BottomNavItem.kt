package com.example.mealmate.navigation

import androidx.annotation.DrawableRes
import com.example.mealmate.R

sealed class BottomNavItem(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int
) {
    object Home : BottomNavItem(Screen.HomeScreen.route, "Home", R.drawable.home)
    object MealPlan :
        BottomNavItem(Screen.MealPlan.route, "Meal Plan", R.drawable.book_mark)

    object ShoppingList :
        BottomNavItem(Screen.ShoppingList.route, "Shopping", R.drawable.shopping_cart)

    object Profile :
        BottomNavItem(Screen.Profile.route, "Profile", R.drawable.profile)
}
