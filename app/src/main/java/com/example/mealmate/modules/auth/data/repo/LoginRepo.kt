package com.example.mealmate.modules.auth.data.repo

import com.example.mealmate.modules.auth.data.model.LoginRequest
import com.example.mealmate.modules.auth.data.model.OTPRequest
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.CustomFailure

interface LoginRepo {
    suspend fun login(request: LoginRequest) : Either<CustomFailure, String>
}