package com.example.mealmate.modules.home.data.repo

import com.example.mealmate.modules.home.data.model.AddToPlanRequest
import com.example.mealmate.modules.home.data.model.CategoryModel
import com.example.mealmate.modules.home.data.model.MealDetailModel
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either

interface HomeRepo {
    suspend fun getCategoryList(): Either<CustomFailure, List<CategoryModel>>
    suspend fun getMealList(categoryId: String?): Either<CustomFailure, List<MealDetailModel>>
    suspend fun addToPlan(request: AddToPlanRequest): Either<CustomFailure, String>
}