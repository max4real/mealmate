package com.example.mealmate.modules.home.data.model

data class AddToPlanRequest(
    val recipeName: String,
    val recipeImgUrl: String,
    val categoryName: String,
    val instruction: String,
    val ingredients: List<IngredientModel>
)


data class IngredientModel(
    val name: String,
    val qty: String
)

