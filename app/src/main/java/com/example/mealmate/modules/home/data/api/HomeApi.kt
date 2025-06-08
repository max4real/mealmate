package com.example.mealmate.modules.home.data.api

import com.example.mealmate.modules.home.data.model.AddToPlanRequest
import com.example.mealmate.modules.home.data.model.CategoryModel
import com.example.mealmate.modules.home.data.model.MealDetailModel
import com.example.mealmate.shared.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeApi {
    @GET("categories")
    suspend fun getCategoryList(): ApiResponse<List<CategoryModel>>

    @GET("meals")
    suspend fun getMealList(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 15,
        @Query("search") search: String? = null,
        @Query("categoryId") categoryId: String? = null
    ): ApiResponse<List<MealDetailModel>>

    @POST("plans")
    suspend fun addToPlan(@Body request: AddToPlanRequest): Response<ApiResponse<Map<String, String>>>
}