package com.example.mealmate.modules.auth.di


import com.example.mealmate.modules.auth.data.api.RegisterApi
import com.example.mealmate.modules.auth.data.repo.RegisterRepo
import com.example.mealmate.modules.auth.data.repo.RegisterRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn (SingletonComponent::class)
class RegisterModule {
    @Provides
    @Singleton
    fun provideRegisterApi(retrofit: Retrofit): RegisterApi = retrofit.create(RegisterApi::class.java)

    @Provides
    @Singleton
    fun provideRegisterRepo(api: RegisterApi) : RegisterRepo = RegisterRepoImpl(api)
}