package com.example.mealmate.modules.shopping_list.di

import com.example.mealmate.modules.shopping_list.data.api.ShoppingListApi
import com.example.mealmate.modules.shopping_list.data.repo.ShoppingListRepo
import com.example.mealmate.modules.shopping_list.data.repo.ShoppingListRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ShoppingListModule {
    @Provides
    @Singleton
    fun provideShoppingListApi(retrofit: Retrofit): ShoppingListApi =
        retrofit.create(ShoppingListApi::class.java)

    @Provides
    @Singleton
    fun provideShoppingListRepo(api: ShoppingListApi): ShoppingListRepo = ShoppingListRepoImpl(api)
}