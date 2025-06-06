package com.example.mealmate.modules.home.data.model

data class MealDetailModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val mealIngredient: List<MealIngredient>,
    val instruction: String,
    val youtubeLink: String?,
    val categoryId: String,
    val category: Category,
    val createdAt: String,
    val updatedAt: String
) {
    fun toAddToPlanRequest(): AddToPlanRequest {
        return AddToPlanRequest(
            recipeName = this.name,
            recipeImgUrl = this.imageUrl,
            categoryName = this.category.name,
            instruction = this.instruction,
            ingredients = this.mealIngredient.map { mealIngredient ->
                IngredientModel(
                    name = mealIngredient.ingredient.name,
                    qty = mealIngredient.measurement.unit
                )
            }
        )
    }
}

data class MealIngredient(
    val ingredient: Ingredient,
    val measurement: Measurement
)

data class Ingredient(
    val name: String,
    val imageUrl: String,
)

data class Measurement(
    val unit: String
)

data class Category(
    val name: String
)