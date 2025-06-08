package com.example.mealmate.shared.model

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class ApiResponse<T>(
    @SerializedName("_metadata") val metadata: Metadata,

    @SerializedName("_data") val data: T,
)

data class Metadata(
    val statusCode: Int, val message: String
)

data class ErrorMeta(
    val message: JsonElement?,            // can be "string" OR ["a","b"]
    val description: String?, val code: Int?, val statusCode: Int?
) {
    fun extractMessage(): String = when {
        message == null -> description ?: "Unknown error"
        message.isJsonPrimitive -> message.asString
        message.isJsonArray && message.asJsonArray.size() > 0 -> message.asJsonArray.joinToString("; ") { it.asString }

        else -> description ?: "Unknown error"
    }
}

data class ErrorResponse(
    @SerializedName("_metadata") val metadata: ErrorMeta
)

// ---------- universal extension  ------------------------------------------
fun <T> Response<T>.parseErrorMessage(): String {
    val body = errorBody()?.string() ?: return "Unknown error"
    return try {
        val apiErr = Gson().fromJson(body, ErrorResponse::class.java)
        apiErr.metadata.extractMessage()
    } catch (e: Exception) {
        "Unknown error"
    }
}