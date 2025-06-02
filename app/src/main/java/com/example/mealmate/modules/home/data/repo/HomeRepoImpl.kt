package com.example.mealmate.modules.home.data.repo

import com.example.mealmate.modules.auth.data.model.LoginRequest
import com.example.mealmate.modules.home.data.api.HomeApi
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.NetworkException
import com.example.mealmate.shared.model.NetworkFailure
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(val api: HomeApi) : HomeRepo {
    override suspend fun login(request: LoginRequest): Either<CustomFailure, String> {
        return try {
            val res = api.login(request)
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