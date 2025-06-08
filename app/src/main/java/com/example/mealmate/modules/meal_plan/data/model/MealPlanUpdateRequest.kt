package com.example.mealmate.modules.meal_plan.data.model

import com.example.mealmate.modules.home.data.model.IngredientModel

data class UpdateMealPlanRequest(
    val ingredients: List<IngredientModel>
)
