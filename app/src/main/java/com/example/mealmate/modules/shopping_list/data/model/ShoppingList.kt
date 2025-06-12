package com.example.mealmate.modules.shopping_list.data.model


data class ShoppingList(
    val id: String,
    val ingredients: List<ShoppingListIngredient>,
    val createdAt: String,
    val updatedAt: String
)

data class ShoppingListIngredient(
    val bought: Boolean,
    val name: String,
    val qty: String,
    val recipeName: String
)
