package com.example.mealmate.modules.home.di

import com.example.mealmate.modules.home.data.api.HomeApi
import com.example.mealmate.modules.home.data.repo.HomeRepo
import com.example.mealmate.modules.home.data.repo.HomeRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {
    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

    @Provides
    @Singleton
    fun provideHomeRepo(api: HomeApi): HomeRepo = HomeRepoImpl(api)
}