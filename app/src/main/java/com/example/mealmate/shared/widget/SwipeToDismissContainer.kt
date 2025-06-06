package com.example.mealmate.shared.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeToDismissContainer(
    item: T,
    onDismiss: (T) -> Unit,
    directions: Set<DismissDirection> = setOf(DismissDirection.EndToStart),
    dismissThreshold: Float = 0.7f,
    backgroundContent: @Composable (DismissDirection?) -> Unit = { direction ->
        val showBackground = direction == DismissDirection.EndToStart
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(if (showBackground) Color.Red else Color.Transparent)
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (showBackground) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }
    },
    content: @Composable () -> Unit
) {
    val dismissState = rememberDismissState(
        confirmStateChange = { dismissValue ->
            when (dismissValue) {
                DismissValue.DismissedToStart -> {
                    onDismiss(item)
                    true
                }

                DismissValue.DismissedToEnd -> {
                    onDismiss(item)
                    true
                }

                else -> false
            }
        }
    )

    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 4.dp),
        directions = directions,
        dismissThresholds = { FractionalThreshold(dismissThreshold) },
        background = { backgroundContent(dismissState.dismissDirection) },
        dismissContent = { content() }
    )
}