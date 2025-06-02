package com.example.mealmate.shared.widget

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mealmate.ui.theme.CustomColors

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    backgroundColor: Color = CustomColors.cardColor,
    shimmerColor: Color = Color.White,
    cornerRadius: Dp = 16.dp
) {
    // Create an infinite animation for shimmer effect
    val transition = rememberInfiniteTransition(label = "ShimmerAnimation")
    val shimmerTranslate = transition.animateFloat(
        initialValue = 0f,
        targetValue = 2500f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ShimmerTranslate"
    )

    // Create shimmer brush
    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            backgroundColor.copy(alpha = 0.9f),
            shimmerColor.copy(alpha = 0.6f),
            backgroundColor.copy(alpha = 0.9f)
        ),
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset(
            x = shimmerTranslate.value,
            y = shimmerTranslate.value
        ),
        tileMode = TileMode.Clamp
    )

    // Draw the box with shimmer brush
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(brush = shimmerBrush)
    )
}
