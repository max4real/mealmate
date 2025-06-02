package com.example.mealmate.modules.auth.data.model

data class VerifyOtpRequest(
    val email: String,
    val code: String
)
