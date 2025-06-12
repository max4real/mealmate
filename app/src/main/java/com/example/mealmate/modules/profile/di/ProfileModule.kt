package com.example.mealmate.modules.profile.di

import com.example.mealmate.modules.profile.data.api.ProfileApi
import com.example.mealmate.modules.profile.data.repo.ProfileRepo
import com.example.mealmate.modules.profile.data.repo.ProfileRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {
    @Provides
    @Singleton
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

    @Provides
    @Singleton
    fun provideProfileRepo(api: ProfileApi): ProfileRepo = ProfileRepoImpl(api)
}