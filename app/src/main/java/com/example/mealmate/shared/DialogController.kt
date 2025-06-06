package com.example.mealmate.shared

import com.example.mealmate.shared.model.DialogState
import com.example.mealmate.shared.model.DialogType

object DialogController {
    private var showDialog: ((DialogState) -> Unit)? = null

    fun initialize(showDialog: (DialogState) -> Unit) {
        this.showDialog = showDialog
    }

    fun showDialog(
        type: DialogType = DialogType.WARNING,
        title: String = "",
        message: String = "",
        onConfirm: () -> Unit = {},
        onDismiss: () -> Unit = {}
    ) {
        showDialog?.invoke(
            DialogState(
                isVisible = true,
                type = type,
                title = title,
                message = message,
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
        )
    }
}