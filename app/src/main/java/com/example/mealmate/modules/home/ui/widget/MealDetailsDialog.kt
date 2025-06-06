package com.example.mealmate.modules.home.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.mealmate.extensions.WidthBox
import com.example.mealmate.modules.home.data.model.MealDetailModel
import com.example.mealmate.ui.theme.CustomColors

@Composable
fun MealDetailsDialog(
    info: MealDetailModel,
    isAddToPlanLoading: Boolean = false,
    onAddToMealPlan: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val title = info.mealName
    val imageUrl = info.mealImage
    val ingredients = info.ingredients
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
                        ingredients.forEach {
                            Text(text = "• $it", color = Color.DarkGray)
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

                // Action button
                Button(
                    onClick = onAddToMealPlan,
                    enabled = !isAddToPlanLoading,
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(alpha = 0.5f),
                        disabledContainerColor = CustomColors.textSecond
                    )
                ) {
                    if (isAddToPlanLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.White
                            )
                            5.WidthBox()
                            Text("Add To Plan")
                        }
                    }
                }
            }
        }
    }
}

