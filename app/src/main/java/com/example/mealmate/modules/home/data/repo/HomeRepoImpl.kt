package com.example.mealmate.modules.home.data.repo

import com.example.mealmate.modules.home.data.api.HomeApi
import com.example.mealmate.modules.home.data.model.AddToPlanRequest
import com.example.mealmate.modules.home.data.model.CategoryModel
import com.example.mealmate.modules.home.data.model.MealDetailModel
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.NetworkException
import com.example.mealmate.shared.model.NetworkFailure
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(val api: HomeApi) : HomeRepo {
    override suspend fun getCategoryList(): Either<CustomFailure, List<CategoryModel>> {
        return try {
            val res = api.getCategoryList()
            if (res.metadata.statusCode == 200 || res.metadata.statusCode == 201) {
                Either.Right(res.data)
            } else {
                throw NetworkException(res.metadata.message)
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }

    override suspend fun getMealList(categoryId: String?): Either<CustomFailure, List<MealDetailModel>> {
        return try {
            val res = api.getMealList(categoryId = categoryId)
            if (res.metadata.statusCode == 200 || res.metadata.statusCode == 201) {
                Either.Right(res.data)
            } else {
                throw NetworkException(res.metadata.message)
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }

    override suspend fun addToPlan(request: AddToPlanRequest): Either<CustomFailure, String> {
        return try {
            val res = api.addToPlan(request)
            if (res.metadata.statusCode == 200 || res.metadata.statusCode == 201) {
                Either.Right(res.metadata.message)
            } else {
                throw NetworkException(res.metadata.message)
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }


}