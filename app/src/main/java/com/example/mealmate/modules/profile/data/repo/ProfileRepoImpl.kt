package com.example.mealmate.modules.profile.data.repo

import com.example.mealmate.modules.profile.data.api.ProfileApi
import com.example.mealmate.modules.profile.data.model.DeleteRequest
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.NetworkException
import com.example.mealmate.shared.model.NetworkFailure
import javax.inject.Inject

class ProfileRepoImpl @Inject constructor(val api: ProfileApi) : ProfileRepo {
    override suspend fun deleteAcc(
    ): Either<CustomFailure, String> {
        return try {
            val res = api.deleteAcc()
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