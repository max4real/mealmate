package com.example.mealmate.modules.auth.data.api

import LoginResponse
import com.example.mealmate.modules.auth.data.model.LoginRequest
import com.example.mealmate.modules.auth.data.model.MeResponse
import com.example.mealmate.shared.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {
    //Login
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>

    @GET("auth/me")
    suspend fun getMe(): ApiResponse<MeResponse>
}