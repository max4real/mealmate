package com.example.mealmate.modules.new_meal.data.repo

import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface NewMealRepo {
    suspend fun postNewMeal(
        recipeName: RequestBody,
        image: MultipartBody.Part,
        instruction: RequestBody,
        ingredientsJson: RequestBody
    ): Either<CustomFailure, String>
}