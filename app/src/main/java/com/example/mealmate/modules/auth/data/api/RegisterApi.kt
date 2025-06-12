package com.example.mealmate.modules.auth.data.api

import com.example.mealmate.modules.auth.data.model.OTPRequest
import com.example.mealmate.modules.auth.data.model.ResendOtpRequest
import com.example.mealmate.modules.auth.data.model.VerifyOtpRequest
import com.example.mealmate.shared.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("auth/register")
    suspend fun registerRequestOtp(
        @Body request: OTPRequest
    ): Response<ApiResponse<Map<String, String>>>


    @POST("auth/verify-email")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): Response<ApiResponse<Map<String, String>>>

    @POST("auth/resend-otp")
    suspend fun resendOtp(
        @Body request: ResendOtpRequest
    ): Response<ApiResponse<Map<String, String>>>

}

