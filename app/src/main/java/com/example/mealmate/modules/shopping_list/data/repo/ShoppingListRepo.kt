package com.example.mealmate.modules.shopping_list.data.repo

import com.example.mealmate.modules.shopping_list.data.model.ShoppingList
import com.example.mealmate.modules.shopping_list.ui.viewmodel.TestListModel
import com.example.mealmate.modules.shopping_list.ui.viewmodel.TestModel
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either

interface ShoppingListRepo {
    suspend fun getShoppingList(): Either<CustomFailure, List<ShoppingList>>
    suspend fun updateCheckedIngredients(ingredients:  TestListModel): Either<CustomFailure, String>
}