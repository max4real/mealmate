package com.example.mealmate.modules.shopping_list.data.repo

import com.example.mealmate.modules.shopping_list.data.api.ShoppingListApi
import com.example.mealmate.modules.shopping_list.data.model.ShoppingList
import com.example.mealmate.modules.shopping_list.ui.viewmodel.TestListModel
import com.example.mealmate.modules.shopping_list.ui.viewmodel.TestModel
import com.example.mealmate.shared.model.CustomFailure
import com.example.mealmate.shared.model.Either
import com.example.mealmate.shared.model.NetworkException
import com.example.mealmate.shared.model.NetworkFailure
import javax.inject.Inject

class ShoppingListRepoImpl @Inject constructor(val api: ShoppingListApi) : ShoppingListRepo {
    override suspend fun getShoppingList(): Either<CustomFailure, List<ShoppingList>> {
        return try {
            val res = api.getShoppingList()
            if (res.metadata.statusCode == 200 || res.metadata.statusCode == 201) {
                Either.Right(res.data)
            } else {
                throw NetworkException(res.metadata.message)
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }

    override suspend fun updateCheckedIngredients(ingredients: TestListModel): Either<CustomFailure, String> {
        return try {
            val res = api.updateCheckedIngredients(ingredients)
            if (res.metadata.statusCode == 200 || res.metadata.statusCode == 201) {
                Either.Right("")
            } else {
                throw NetworkException(res.metadata.message)
            }
        } catch (e: Exception) {
            Either.Left(NetworkFailure(e.message))
        }
    }
}