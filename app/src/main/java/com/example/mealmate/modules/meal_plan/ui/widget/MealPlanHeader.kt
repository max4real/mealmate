package com.example.mealmate.modules.meal_plan.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.extensions.WidthBox
import com.example.mealmate.ui.theme.CustomColors

@Composable
fun MealPlanHeader(
    onClickAddMeal: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "My Meal Plan",
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onClickAddMeal,
            enabled = true,
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .padding(top = 12.dp)
                .height(35.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContentColor = Color.White.copy(alpha = 0.5f),
                disabledContainerColor = CustomColors.textSecond
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
                5.WidthBox()
                Text("Add Meal")
            }
        }
    }
}