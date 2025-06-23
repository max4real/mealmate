package com.example.mealmate.modules.meal_plan.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.mealmate.modules.auth.ui.widget.CustomInputField
import com.example.mealmate.modules.home.data.model.IngredientModel
import com.example.mealmate.modules.meal_plan.data.model.MealPlanModel
import com.example.mealmate.ui.theme.CustomColors

@Composable
fun MealPlanDialog(
    info: MealPlanModel,
    onDismissRequest: () -> Unit,
    onSave: (MealPlanModel) -> Unit
) {
    val title = info.recipeName
    val imageUrl = info.recipeImage
    val instructions = info.instruction

    val isEditing = remember { mutableStateOf(false) }

    val ingredients = remember(info.id) {
        info.ingredients.toMutableStateList()
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
//                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .heightIn(min = 400.dp, max = 600.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (isEditing.value) {
                        TextButton(onClick = {
                            onSave(
                                info.copy(ingredients = ingredients.toList())
                            )
                            isEditing.value = false
                        }
                        ) {
                            Text("Save")
                        }
                    } else {
                        TextButton(onClick = { isEditing.value = true }) { Text("Edit") }
                    }

                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Default.Close, null, tint = Color.Gray)
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Meal Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )

//                    Text(
//                        text = "Ingredients (Name - Qty)",
//                        style = MaterialTheme.typography.titleSmall,
//                        color = Color.Black
//                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Ingredients (Name - Qty)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (isEditing.value) {
                            IconButton(
                                onClick = { ingredients.add(IngredientModel("", "")) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // ───────── EXISTING INGREDIENT ROWS ─────────
                        ingredients.forEachIndexed { index, item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (isEditing.value) {
                                    /* name */
                                    CustomInputField(
                                        value = item.name,
                                        label = "Name",
                                        keyboardType = KeyboardType.Text,
                                        onValueChange = { newName ->
                                            ingredients[index] = item.copy(name = newName)
                                        },
                                        modifier = Modifier.weight(3f)
                                    )
                                    CustomInputField(
                                        value = item.qty,
                                        label = "Qty",
                                        keyboardType = KeyboardType.Text,
                                        onValueChange = { newQty ->
                                            ingredients[index] = item.copy(qty = newQty)
                                        },
                                        modifier = Modifier.weight(2f)
                                    )

                                    // Delete icon
                                    IconButton(onClick = { ingredients.removeAt(index) }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Remove",
                                            tint = CustomColors.red
                                        )
                                    }
                                } else {
                                    Text("• ${item.name} (${item.qty})")
                                }
                            }
                        }

                    }

                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black
                    )

                    Text(
                        text = instructions,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

