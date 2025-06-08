package com.example.mealmate.modules.meal_plan.data.repo

import com.example.mealmate.modules.meal_plan.data.model.MealPlanModel
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either

interface MealPlanRepo {
    suspend fun getMealPlan(): Either<CustomFailure, List<MealPlanModel>>
    suspend fun updateMealPlan(info: MealPlanModel): Either<CustomFailure, String>
    suspend fun deleteMealPlan(mealId: String): Either<CustomFailure, String>

}