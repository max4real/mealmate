package com.example.mealmate.modules.new_meal.di

import com.example.mealmate.modules.new_meal.data.api.NewMealApi
import com.example.mealmate.modules.new_meal.data.repo.NewMealRepo
import com.example.mealmate.modules.new_meal.data.repo.NewMealRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewMealModule {
    @Provides
    @Singleton
    fun provideNewMealApi(retrofit: Retrofit): NewMealApi = retrofit.create(NewMealApi::class.java)

    @Provides
    @Singleton
    fun provideNewMealRepo(api: NewMealApi): NewMealRepo = NewMealRepoImpl(api)
}