package com.example.mealmate.modules.auth.data.repo

import com.example.mealmate.modules.auth.data.api.RegisterApi
import com.example.mealmate.modules.auth.data.model.OTPRequest
import com.example.mealmate.modules.auth.data.model.ResendOtpRequest
import com.example.mealmate.modules.auth.data.model.VerifyOtpRequest
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.NetworkFailure
import com.example.mealmate.shared.model.parseErrorMessage
import javax.inject.Inject

class RegisterRepoImpl @Inject constructor(val api: RegisterApi) : RegisterRepo {
    override suspend fun registerRequestOtp(request: OTPRequest): Either<CustomFailure, String> {
        return try {
            val res = api.registerRequestOtp(request)
            if (res.isSuccessful) {
                Either.Right(res.body()!!.metadata.message)
            } else {
                val msg = res.parseErrorMessage()
                Either.Left(NetworkFailure(msg))
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }

    override suspend fun verifyOtpRequest(request: VerifyOtpRequest): Either<CustomFailure, String> {
        return try {
            val res = api.verifyOtp(request)
//            if (res.metadata.statusCode in 200..210) {
//                Either.Right(res.metadata.message)
//            } else {
//                throw NetworkException(res.metadata.message)
//            }
            if (res.isSuccessful) {
                Either.Right(res.body()!!.metadata.message)
            } else {
                val msg = res.parseErrorMessage()
                Either.Left(NetworkFailure(msg))
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }

    override suspend fun resendOtpRequest(request: ResendOtpRequest): Either<CustomFailure, String> {
        return try {
            val res = api.resendOtp(request)
//            if (res.metadata.statusCode in 200..210) {
//                Either.Right(res.metadata.message)
//            } else {
//                throw NetworkException(res.metadata.message)
//            }
            if (res.isSuccessful) {
                Either.Right(res.body()!!.metadata.message)
            } else {
                val msg = res.parseErrorMessage()
                Either.Left(NetworkFailure(msg))
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }
}