package com.example.mealmate.shared.managers

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mealmate.modules.shopping_list.data.model.ShoppingListIngredient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val prefs = EncryptedSharedPreferences.create(
        "secure_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveToken(token: String) {
        prefs.edit { putString(KEY_TOKEN, token) }
    }

    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun clearToken() {
        prefs.edit { remove(KEY_TOKEN) }
    }

    fun saveCheckedIngredients(ingredients: Map<String, ShoppingListIngredient>) {
        prefs.edit {
            val simpleMap = ingredients.mapValues { (_, ingredient) ->
                "${ingredient.bought}|${ingredient.name}|${ingredient.qty}|${ingredient.recipeName}"
            }
            val jsonString = Json.encodeToString(
                MapSerializer(String.serializer(), String.serializer()), simpleMap
            )
            putString(CHECKED_INGREDIENTS, jsonString)
        }
    }

    fun getCheckedIngredients(): Map<String, ShoppingListIngredient> {
        return try {
            val jsonString = prefs.getString(CHECKED_INGREDIENTS, null) ?: return emptyMap()
            val simpleMap = Json.decodeFromString(
                MapSerializer(String.serializer(), String.serializer()), jsonString
            )
            simpleMap.mapValues { (_, value) ->
                val parts = value.split("|")
                ShoppingListIngredient(
                    bought = parts[0].toBoolean(),
                    name = parts[1],
                    qty = parts[2],
                    recipeName = parts[3]
                )
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }

    fun clearCheckedIngredients() {
        prefs.edit { remove(CHECKED_INGREDIENTS) }
    }

    companion object {
        private const val KEY_TOKEN = "key_token"
        private const val CHECKED_INGREDIENTS = "checked_ingredients"

    }
}