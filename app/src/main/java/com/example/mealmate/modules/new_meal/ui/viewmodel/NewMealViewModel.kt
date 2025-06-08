package com.example.mealmate.modules.new_meal.ui.viewmodel


import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mealmate.modules.home.data.model.IngredientModel
import com.example.mealmate.modules.new_meal.data.repo.NewMealRepo
import com.example.mealmate.shared.DialogController
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import com.example.mealmate.shared.model.DialogType
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class NewMealViewModel @Inject constructor(
    private val repo: NewMealRepo,
    private val sessionManager: SessionManager,
    private val tokenManager: TokenManager,
    private val dialogController: DialogController
) : ViewModel() {
    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> get() = _userName

    init {
        _userName.value = sessionManager.me?.name
    }

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _loading

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri
    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    private val _mealName = MutableStateFlow<String>("")
    val mealName: StateFlow<String> = _mealName

    fun setMealName(value: String) {
        _mealName.value = value
    }

    private val _instruction = MutableStateFlow<String>("")
    val instruction: StateFlow<String> = _instruction

    fun setInstruction(value: String) {
        _instruction.value = value
    }

    private val _ingredientList = MutableStateFlow(mutableStateListOf<IngredientModel>())
    val ingredientList: StateFlow<List<IngredientModel>> get() = _ingredientList

    fun addIngredient() = _ingredientList.value.add(IngredientModel("", ""))
    fun updateIngredient(index: Int, newVal: IngredientModel) {
        _ingredientList.value[index] = newVal
    }

    fun removeIngredient(index: Int) = _ingredientList.value.removeAt(index)

    fun onClickSave(context: Context, appnavi: NavHostController) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val namePart = mealName.value.toRequestBody("text/plain".toMediaType())
                val instructionPart = instruction.value.toRequestBody("text/plain".toMediaType())

                // 1. Image file (replace with actual file URI)
                val file = uriToFile(context, imageUri.value!!)
                val imageRequest = file
                    .asRequestBody("image/*".toMediaType())
                val imagePart = MultipartBody.Part.createFormData("image", file.name, imageRequest)

                // 2. Ingredients as JSON string
                val ingredientsJson = Gson().toJson(ingredientList.value)
                val ingredientsPart =
                    ingredientsJson.toRequestBody("application/json".toMediaType())

                val result = repo.postNewMeal(
                    recipeName = namePart,
                    image = imagePart,
                    instruction = instructionPart,
                    ingredientsJson = ingredientsPart
                )

                result.fold(
                    onLeft = { failure ->
                        /* handle */
                        _loading.value = false
                        _errorMessage.value = failure.errorMessage
                    },
                    onRight = { message ->
                        _loading.value = false
                        appnavi.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("REFRESH_MEAL_PLAN", true)
                        appnavi.popBackStack()
                        dialogController.showDialog(
                            type = DialogType.SUCCESS,
                            title = "Success",
                            message = message,
                            onConfirm = { },
                            onDismiss = { }
                        )
                    }
                )
            } catch (e: Exception) {
                // handle error
                dialogController.showDialog(
                    type = DialogType.ERROR,
                    title = "Error",
                    message = "Something went wrong",
                    onConfirm = { },
                    onDismiss = { }
                )
            }
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open input stream for URI")
        val tempFile = File.createTempFile("upload_", ".tmp", context.cacheDir)
        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return tempFile
    }

}