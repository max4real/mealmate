package com.example.mealmate.di

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.example.mealmate.shared.model.DialogState
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MealMateApp : Application() {
    val dialogState = mutableStateOf(DialogState())

    override fun onCreate() {
        super.onCreate()
    }
}
