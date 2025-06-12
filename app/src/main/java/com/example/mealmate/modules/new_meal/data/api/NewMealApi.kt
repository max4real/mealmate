package com.example.mealmate.modules.new_meal.data.api

import com.example.mealmate.shared.model.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NewMealApi {
    @Multipart
    @POST("plans/manual")
    suspend fun postNewMeal(
        @Part("recipeName") recipeName: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("instruction") instruction: RequestBody,
        @Part("ingredients") ingredientsJson: RequestBody
    ): ApiResponse<Map<String, String>>
}