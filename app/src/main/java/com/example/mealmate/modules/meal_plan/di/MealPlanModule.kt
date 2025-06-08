package com.example.mealmate.modules.meal_plan.di

import com.example.mealmate.modules.meal_plan.data.api.MealPlanApi
import com.example.mealmate.modules.meal_plan.data.repo.MealPlanRepo
import com.example.mealmate.modules.meal_plan.data.repo.MealPlanRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MealPlanModule {
    @Provides
    @Singleton
    fun provideMealPlanApi(retrofit: Retrofit): MealPlanApi = retrofit.create(MealPlanApi::class.java)

    @Provides
    @Singleton
    fun provideMealPlanRepo(api: MealPlanApi): MealPlanRepo = MealPlanRepoImpl(api)
}