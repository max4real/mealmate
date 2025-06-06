package com.example.mealmate.modules.home.data.api

import com.example.mealmate.modules.auth.data.model.LoginRequest
import com.example.mealmate.modules.auth.data.model.OTPRequest
import com.example.mealmate.shared.model.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface HomeApi {

    //Login
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<String>

    @POST("auth/login-request-otp")
//    @Headers("x-api-key: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.ENTzIuKLi4L4MsDVNSoO0ZIjOi1hToEPKJAMgNB0G7o")
    suspend fun loginRequestOtp(@Body request: OTPRequest): ApiResponse<String>

}