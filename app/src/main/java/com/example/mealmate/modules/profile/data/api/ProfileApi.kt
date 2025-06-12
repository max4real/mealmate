package com.example.mealmate.modules.profile.data.api

import com.example.mealmate.modules.profile.data.model.DeleteRequest
import com.example.mealmate.shared.model.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST

interface ProfileApi {
    @DELETE("user")
    suspend fun deleteAcc(): ApiResponse<Map<String, String>>
}