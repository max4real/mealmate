package com.example.mealmate.modules.new_meal.ui.screen

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.extensions.HeightBox
import com.example.mealmate.extensions.WidthBox
import com.example.mealmate.modules.auth.ui.widget.CustomInputField
import com.example.mealmate.modules.new_meal.ui.viewmodel.NewMealViewModel
import com.example.mealmate.shared.widget.CustomAppBar
import com.example.mealmate.shared.widget.MultilineInputField
import com.example.mealmate.ui.theme.CustomColors

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun NewMealPage(appNavi: NavHostController) {
    val vm = hiltViewModel<NewMealViewModel>()

    val imageUri = vm.imageUri.collectAsState()
    val mealName = vm.mealName.collectAsState()
    val instruction = vm.instruction.collectAsState()
    val ingredients = vm.ingredientList.collectAsState()
    val errorMessage = vm.errorMessage.collectAsState()
    val loading = vm.loading.collectAsState()

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            vm.setImageUri(it)
            val source = ImageDecoder.createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
    }

    Column(Modifier.systemBarsPadding()) {
        CustomAppBar(
            userName = vm.userName.collectAsState().value,
            showLogo = false,
            includeBackKey = true,
            onClickBack = { appNavi.popBackStack() })
        10.HeightBox()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            /* ─── Header ─── */
            item {
                Text("Create Custom Recipe", fontSize = 18.sp, fontWeight = FontWeight.W500)
            }

            /* ─── Image picker ─── */
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF1F1F1))
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (bitmap.value == null) {
                        Text("Tap to pick image", color = Color.Gray)
                    } else {
                        Image(
                            bitmap = bitmap.value!!.asImageBitmap(),
                            contentDescription = "Meal Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            /* ─── Meal name ─── */
            item {
                Column {
                    Text("Meal Name", fontSize = 16.sp, fontWeight = FontWeight.W500)
                    CustomInputField(
                        label = "Meal name",
                        value = mealName.value,
                        keyboardType = KeyboardType.Text,
                        onValueChange = vm::setMealName
                    )
                }
            }

            /* ─── Ingredients list ─── */
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Ingredients", fontSize = 16.sp, fontWeight = FontWeight.W500)
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = vm::addIngredient
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }

                    ingredients.value.forEachIndexed { idx, ing ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            /* qty */
                            CustomInputField(
                                value = ing.qty,
                                label = "Qty",
                                keyboardType = KeyboardType.Text,
                                onValueChange = { vm.updateIngredient(idx, ing.copy(qty = it)) },
                                modifier = Modifier.weight(1f)
                            )
                            8.WidthBox()

                            /* name */
                            CustomInputField(
                                value = ing.name,
                                label = "Name",
                                keyboardType = KeyboardType.Text,
                                onValueChange = { vm.updateIngredient(idx, ing.copy(name = it)) },
                                modifier = Modifier.weight(3f)
                            )
                            IconButton(onClick = { vm.removeIngredient(idx) }) {
                                Icon(
                                    Icons.Default.Clear, null, tint = CustomColors.red
                                )
                            }
                        }
                    }
                }
            }

            /* ─── Instructions ─── */
            item {
                Column {
                    Text("Instructions", fontSize = 16.sp, fontWeight = FontWeight.W500)
                    MultilineInputField(
                        value = instruction.value,
                        label = "Instructions",
                        onValueChange = vm::setInstruction
                    )
                }
            }

            item {
                if (errorMessage.value.isNotEmpty())
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        text = errorMessage.value,
                        fontSize = 16.sp,
                        color = CustomColors.red,
                        textAlign = TextAlign.Center
                    )
            }

            /* ─── Continue button ─── */
            item {
                Button(
                    enabled = !loading.value && mealName.value.isNotEmpty() && instruction.value.isNotEmpty() && ingredients.value.isNotEmpty() && imageUri.value != null,
                    onClick = {
                        vm.onClickSave(context = context, appnavi = appNavi)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(.5f),
                        disabledContainerColor = CustomColors.textSecond
                    )
                ) {
                    if (loading.value) CircularProgressIndicator(
                        modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = Color.White
                    ) else {
                        Text("Continue")
                    }
                }
            }
        }
    }
}

