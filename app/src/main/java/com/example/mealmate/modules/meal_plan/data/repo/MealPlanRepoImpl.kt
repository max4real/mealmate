package com.example.mealmate.modules.meal_plan.data.repo

import com.example.mealmate.modules.meal_plan.data.api.MealPlanApi
import com.example.mealmate.modules.meal_plan.data.model.MealPlanModel
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.NetworkException
import com.example.mealmate.shared.model.NetworkFailure
import javax.inject.Inject

class MealPlanRepoImpl @Inject constructor(val api: MealPlanApi) : MealPlanRepo {
    override suspend fun getMealPlan(): Either<CustomFailure, List<MealPlanModel>> {
        return try {
            val res = api.getMealPlan()
            if (res.metadata.statusCode == 200 || res.metadata.statusCode == 201) {
                Either.Right(res.data)
            } else {
                throw NetworkException(res.metadata.message)
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }
}