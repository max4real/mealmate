package com.example.mealmate.modules.meal_plan.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mealmate.modules.home.data.model.MealDetailModel
import com.example.mealmate.modules.meal_plan.data.model.MealPlanModel
import com.example.mealmate.ui.theme.CustomColors

@Composable
fun MealListCard(
    info: MealPlanModel,
    onViewClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CustomColors.cardColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(info.recipeImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .width(120.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        bottomStart = 12.dp
                    )
                )
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(CustomColors.cardColor)
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = info.recipeName,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Button(
                onClick = onViewClick,
                modifier = Modifier
                    .align(Alignment.End)
                    .height(32.dp), // reduce button height
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 4.dp
                ), // tighter padding
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(
                    text = "View Meal",
                    fontSize = 12.sp // smaller text
                )
            }
        }
    }
}
