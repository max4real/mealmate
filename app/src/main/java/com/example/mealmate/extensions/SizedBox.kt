package com.example.mealmate.extensions

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Int.WidthBox() {
    Spacer(modifier = Modifier.width(this.dp))
}

@Composable
fun Int.HeightBox() {
    Spacer(modifier = Modifier.height(this.dp))
}

@Composable
fun Double.WidthBox() {
    Spacer(modifier = Modifier.width(this.dp))
}

@Composable
fun Double.HeightBox() {
    Spacer(modifier = Modifier.height(this.dp))
}