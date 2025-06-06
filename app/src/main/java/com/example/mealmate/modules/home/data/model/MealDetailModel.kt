package com.example.mealmate.modules.home.data.model

data class MealDetailModel(
    val mealId: String,
    val mealName: String,
    val mealImage: String,
    val ingredients: List<String>,
    val instruction: String
)
