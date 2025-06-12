package com.example.mealmate.modules.shopping_list.data.api

import com.example.mealmate.modules.shopping_list.data.model.ShoppingList
import com.example.mealmate.shared.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface ShoppingListApi {
    @GET("plans/shopping")
    suspend fun getShoppingList(): ApiResponse<List<ShoppingList>>

    @POST("plans/shopping")
    suspend fun updateCheckedIngredients(): ApiResponse<Map<String, String>>

}