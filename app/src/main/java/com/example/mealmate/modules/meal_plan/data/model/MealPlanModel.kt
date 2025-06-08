package com.example.mealmate.modules.meal_plan.data.model

import com.example.mealmate.modules.home.data.model.IngredientModel

data class MealPlanModel(
    val id: String,
    val recipeName: String,
    val recipeImage: String,
    val categoryName: String?,
    val ingredients: List<IngredientModel>,
    val instruction: String,
    val startDate: String,
    val endDate: String,
    val done: Boolean,
    val deleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val shoppingId: String,
    val userId: String
)