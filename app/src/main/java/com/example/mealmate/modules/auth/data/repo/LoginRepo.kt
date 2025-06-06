package com.example.mealmate.modules.auth.data.repo

import com.example.mealmate.modules.auth.data.model.LoginRequest
import com.example.mealmate.modules.auth.data.model.MeResponse
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either

interface LoginRepo {
    suspend fun login(request: LoginRequest): Either<CustomFailure, String>
    suspend fun getMe(): Either<CustomFailure, MeResponse>
}