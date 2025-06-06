package com.example.mealmate.shared.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mealmate.extensions.WidthBox
import com.example.mealmate.shared.model.DialogState
import com.example.mealmate.shared.model.DialogType
import com.example.mealmate.ui.theme.CustomColors


@Composable
fun ConfirmationDialog(
    state: DialogState, onDismissRequest: () -> Unit
) {
    if (state.isVisible) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            icon = {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = when (state.type) {
                        DialogType.WARNING -> Icons.Default.Warning
                        DialogType.SUCCESS -> Icons.Default.CheckCircle
                        DialogType.ERROR -> Icons.Default.Close
                        DialogType.INFO -> Icons.Default.Info
                    },
                    contentDescription = null, tint = when (state.type) {
                        DialogType.WARNING -> Color(0xFFFFA000) // Amber
                        DialogType.SUCCESS -> Color(0xFF4CAF50) // Green
                        DialogType.ERROR -> Color(0xFFF44336)  // Red
                        DialogType.INFO -> Color(0xFF2196F3)   // Blue
                    }
                )
            },
            title = {
                Text(text = state.title, style = MaterialTheme.typography.headlineSmall)
            },
            text = {
                Text(text = state.message, style = MaterialTheme.typography.bodyMedium)
            },
            confirmButton = {
                Row {
                    OutlinedButton(
                        onClick = {
                            state.onDismiss()
                            onDismissRequest()
                        },
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(35.dp)
                            .padding(end = 4.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black,
                            disabledContentColor = Color.Black.copy(alpha = 0.5f)
                        ),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text("Cancel")
                    }
                    20.WidthBox()
                    Button(
                        onClick = {
                            state.onConfirm()
                            onDismissRequest()
                        },
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(35.dp)
                            .padding(start = 4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White,
                            disabledContentColor = Color.White.copy(alpha = 0.5f),
                            disabledContainerColor = CustomColors.textSecond
                        )
                    ) {
                        Text("Confirm")
                    }
                }
            },
        )
    }
}