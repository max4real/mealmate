package com.example.mealmate.modules.auth.data.model

data class OTPRequest(
    val name: String,
    val email: String,
    val password: String
)