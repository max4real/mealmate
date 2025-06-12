package com.example.mealmate.modules.shopping_list.data.api

import com.example.mealmate.modules.shopping_list.data.model.ShoppingList
import com.example.mealmate.modules.shopping_list.ui.viewmodel.TestListModel
import com.example.mealmate.modules.shopping_list.ui.viewmodel.TestModel
import com.example.mealmate.shared.model.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ShoppingListApi {
    @GET("plans/shopping")
    suspend fun getShoppingList(): ApiResponse<List<ShoppingList>>

    @PATCH("plans/shopping")
    suspend fun updateCheckedIngredients(@Body info: TestListModel): ApiResponse<Map<String, String>>

}