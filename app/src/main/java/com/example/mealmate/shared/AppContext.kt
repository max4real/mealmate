package com.example.mealmate.shared

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.mealmate.shared.model.DialogState


val LocalDialogState = staticCompositionLocalOf<MutableState<DialogState>> {
    error("DialogState not provided")
}