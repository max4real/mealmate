package com.example.mealmate.modules.auth.data.repo

import com.example.mealmate.modules.auth.data.api.LoginApi
import com.example.mealmate.modules.auth.data.model.LoginRequest
import com.example.mealmate.modules.auth.data.model.MeResponse
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.NetworkException
import com.example.mealmate.shared.model.NetworkFailure
import javax.inject.Inject

class LoginRepoImpl @Inject constructor(val api: LoginApi) : LoginRepo {
    override suspend fun login(request: LoginRequest): Either<CustomFailure, String> {
        return try {
            val res = api.login(request)
            if (res.metadata.statusCode in 200..210) {
                Either.Right(res.data.token)
            } else {
                throw NetworkException(res.metadata.message)
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }

    override suspend fun getMe(): Either<CustomFailure, MeResponse> {
        return try {
            val res = api.getMe()
            if (res.metadata.statusCode in 200..210) {
                Either.Right(res.data)
            } else {
                throw NetworkException(res.metadata.message)
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }
}