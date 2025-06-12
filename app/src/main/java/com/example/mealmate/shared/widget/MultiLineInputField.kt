package com.example.mealmate.shared.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.ui.theme.CustomColors

@Composable
fun MultilineInputField(
    value: String,
    label: String,
    minLines: Int = 6,
    fontSize : TextUnit = 16.sp,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
            .padding(8.dp)
    ) {
        if (value.isEmpty()) Text(label, color = CustomColors.textSecond2)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = fontSize, color = CustomColors.textMain),
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 20,   // let it grow after 6 lines if needed
            minLines = minLines
        )
    }
}
