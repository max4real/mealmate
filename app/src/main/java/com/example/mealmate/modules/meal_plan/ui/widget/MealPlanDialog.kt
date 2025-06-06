package com.example.mealmate.modules.meal_plan.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.mealmate.modules.meal_plan.data.model.MealPlanModel

@Composable
fun MealPlanDialog(
    info: MealPlanModel,
    onDismissRequest: () -> Unit
) {
    val title = info.recipeName
    val imageUrl = info.recipeImage
    val instructions = info.instruction

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
                // App bar style title row
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
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.Gray
                        )
                    }
                }

                // Scrollable content
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

                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        info.ingredients.forEach { mealIngredient ->
                            val name = mealIngredient.name
                            val unit = mealIngredient.qty
                            Text(text = "• $name ($unit)", color = Color.DarkGray)
                        }
                    }
//                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                        info.ingredients.forEach { item ->
//
//                            Text(
//                                text = "${item.name} (${item.qty})",
//                                modifier = Modifier.padding(start = 8.dp),
//                                color = Color.DarkGray
//                            )
//                        }
//                    }

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

