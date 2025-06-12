package com.example.mealmate.modules.shopping_list.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.mealmate.extensions.HeightBox
import com.example.mealmate.modules.shopping_list.data.model.ShoppingListIngredient
import com.example.mealmate.ui.theme.CustomColors

@Composable
fun ShoppingListCard(
    ingredient: ShoppingListIngredient, onCheckedChange: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(ingredient.bought) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CustomColors.cardColor)
            .clickable {
                isChecked = !isChecked
                onCheckedChange(isChecked)
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                onCheckedChange(it)
            },
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = "${ingredient.name} - ${ingredient.qty}", style = TextStyle(
                    textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
                )
            )
            5.HeightBox()
            Text(
                text = "For : ${ingredient.recipeName}", style = TextStyle(
                    textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
                )
            )
        }
    }
}
