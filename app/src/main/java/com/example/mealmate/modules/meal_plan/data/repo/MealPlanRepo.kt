package com.example.mealmate.modules.meal_plan.data.repo

import com.example.mealmate.modules.auth.data.model.LoginRequest
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either

interface MealPlanRepo {
    suspend fun login(request: LoginRequest) : Either<CustomFailure, String>
}