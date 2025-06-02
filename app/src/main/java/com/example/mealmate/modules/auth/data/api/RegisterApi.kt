package com.example.mealmate.modules.auth.data.api

import com.example.mealmate.modules.auth.data.model.OTPRequest
import com.example.mealmate.modules.auth.data.model.ResendOtpRequest
import com.example.mealmate.modules.auth.data.model.VerifyOtpRequest
import com.example.mealmate.shared.model.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    //Register
    @POST("auth/register")
    suspend fun registerRequestOtp(@Body request: OTPRequest): ApiResponse<Map<String, String>>

    @POST("auth/verify-email")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): ApiResponse<Map<String, String>>

    @POST("auth/resend-otp")
    suspend fun resendOtp(@Body request: ResendOtpRequest): ApiResponse<Map<String, String>>

//    @POST("auth/register")
//    @Headers("x-api-key: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.ENTzIuKLi4L4MsDVNSoO0ZIjOi1hToEPKJAMgNB0G7o")
//    suspend fun register(@Body request: RegisterRequest): ApiResponse<String>
}