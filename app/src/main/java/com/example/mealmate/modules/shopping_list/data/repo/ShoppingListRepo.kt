package com.example.mealmate.modules.shopping_list.data.repo

import com.example.mealmate.modules.auth.data.model.LoginRequest
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.CustomFailure

interface ShoppingListRepo {
    suspend fun login(request: LoginRequest) : Either<CustomFailure, String>
}