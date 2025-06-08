package com.example.mealmate.modules.meal_plan.data.api

import com.example.mealmate.modules.home.data.model.IngredientModel
import com.example.mealmate.modules.meal_plan.data.model.MealPlanModel
import com.example.mealmate.modules.meal_plan.data.model.UpdateMealPlanRequest
import com.example.mealmate.shared.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface MealPlanApi {
    @GET("plans")
    suspend fun getMealPlan(): ApiResponse<List<MealPlanModel>>

    @PATCH("plans/{id}")
    suspend fun updateMealPlan(
        @Path("id") mealId: String,
        @Body info: UpdateMealPlanRequest
    ): Response<ApiResponse<Map<String, String>>>

    @DELETE("plans/{id}")
    suspend fun deleteMealPlan(@Path("id") mealId: String): Response<ApiResponse<Map<String, String>>>
}