package com.example.mealmate.shared.model

data class DialogState(
    val isVisible: Boolean = false,
    val type: DialogType = DialogType.WARNING,
    val title: String = "",
    val message: String = "",
    val onConfirm: () -> Unit = {},
    val onDismiss: () -> Unit = {}
)