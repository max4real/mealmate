package com.example.mealmate.modules.shopping_list.data.repo

import com.example.mealmate.modules.shopping_list.data.model.ShoppingList
import com.example.mealmate.modules.shopping_list.data.model.ShoppingListIngredient
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either

interface ShoppingListRepo {
    suspend fun getShoppingList(): Either<CustomFailure, List<ShoppingList>>
    suspend fun updateCheckedIngredients(ingredients: List<ShoppingListIngredient>): Either<CustomFailure, Unit>
}