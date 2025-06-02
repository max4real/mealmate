package com.example.mealmate.shared.model

sealed class CustomFailure(
    val errorMessage: String,
)

class NetworkFailure(
    errorMessage: String?,
) : CustomFailure(errorMessage ?: "Something went wrong!")