package com.example.mealmate.modules.profile.data.repo

import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either

interface ProfileRepo {
    suspend fun deleteAcc(): Either<CustomFailure, String>
}