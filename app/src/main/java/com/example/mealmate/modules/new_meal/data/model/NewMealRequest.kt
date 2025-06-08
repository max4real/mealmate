package com.example.mealmate.modules.new_meal.data.model

import com.example.mealmate.modules.home.data.model.IngredientModel

data class NewMealRequest(
    val recipeName: String,
    val image: String,
    val categoryName: String,
    val instruction: String,
    val ingredients: List<IngredientModel>
)
