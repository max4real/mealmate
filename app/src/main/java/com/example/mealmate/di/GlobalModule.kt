package com.example.mealmate.di

import android.content.Context
import com.example.mealmate.modules.auth.data.AuthInterceptor
import com.example.mealmate.shared.DialogController
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlobalModule {
    @Provides
    fun baseUrl() = "https://api.mealmate.shwepyaesone.com/api/v1/"
//    fun baseUrl() = "http://192.168.31.82:4005/api/v1/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideUrl(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager()
    }

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): MealMateApp {
        return app as MealMateApp
    }

    @Provides
    @Singleton
    fun provideDialogController(app: MealMateApp): DialogController {
        DialogController.initialize { newState ->
            app.dialogState.value = newState
        }
        return DialogController
    }

    @Provides
    @Singleton
    fun provideDialogState(app: MealMateApp) = app.dialogState
}