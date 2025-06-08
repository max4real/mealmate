package com.example.mealmate.modules.new_meal.data.repo

import com.example.mealmate.modules.new_meal.data.api.NewMealApi
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.NetworkException
import com.example.mealmate.shared.model.NetworkFailure
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class NewMealRepoImpl @Inject constructor(val api: NewMealApi) : NewMealRepo {
    override suspend fun postNewMeal(
        recipeName: RequestBody,
        image: MultipartBody.Part,
        instruction: RequestBody,
        ingredientsJson: RequestBody
    ): Either<CustomFailure, String> {
        return try {
            val res = api.postNewMeal(recipeName, image, instruction, ingredientsJson)
            if (res.metadata.statusCode in 200..210) {
                Either.Right(res.metadata.message)
            } else {
                throw NetworkException(res.metadata.message)
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }
}