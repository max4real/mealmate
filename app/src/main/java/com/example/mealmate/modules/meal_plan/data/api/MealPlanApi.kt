package com.example.mealmate.modules.meal_plan.data.api

import com.example.mealmate.modules.home.data.model.CategoryModel
import com.example.mealmate.modules.meal_plan.data.model.MealPlanModel
import com.example.mealmate.shared.model.ApiResponse
import retrofit2.http.GET

interface MealPlanApi {
    @GET("plans")
    suspend fun getMealPlan(): ApiResponse<List<MealPlanModel>>
}