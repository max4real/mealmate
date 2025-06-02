package com.example.mealmate.shared.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("_metadata")
    val metadata: Metadata,

    @SerializedName("_data")
    val data: T,
)

data class Metadata(
    val statusCode: Int,
    val message: String
)