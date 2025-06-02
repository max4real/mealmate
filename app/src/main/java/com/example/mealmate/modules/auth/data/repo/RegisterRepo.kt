package com.example.mealmate.modules.auth.data.repo

import com.example.mealmate.modules.auth.data.model.OTPRequest
import com.example.mealmate.modules.auth.data.model.ResendOtpRequest
import com.example.mealmate.modules.auth.data.model.VerifyOtpRequest
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.CustomFailure

interface RegisterRepo {
    suspend fun registerRequestOtp(request: OTPRequest) : Either<CustomFailure, String>
    suspend fun verifyOtpRequest(request: VerifyOtpRequest) : Either<CustomFailure, String>
    suspend fun resendOtpRequest(request: ResendOtpRequest) : Either<CustomFailure, String>

//    suspend fun register(request: RegisterRequest) : Either<CustomFailure, String>
}