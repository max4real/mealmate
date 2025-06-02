package com.example.mealmate.modules.auth.di

import com.example.mealmate.modules.auth.data.api.LoginApi
import com.example.mealmate.modules.auth.data.repo.LoginRepo
import com.example.mealmate.modules.auth.data.repo.LoginRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn (SingletonComponent::class)
class LoginModule {
    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi = retrofit.create(LoginApi::class.java)

    @Provides
    @Singleton
    fun provideLoginRepo(api: LoginApi) : LoginRepo = LoginRepoImpl(api)
}