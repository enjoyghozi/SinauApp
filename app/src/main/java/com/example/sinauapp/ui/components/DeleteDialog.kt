package com.example.sinauapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    isOpen: Boolean,
    title: String,
    bodyText: String,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(
                text = title,
                fontFamily = MaterialTheme.typography.titleSmall.fontFamily
            ) },
            text = {
                Text(
                    text = bodyText,
                    fontFamily = MaterialTheme.typography.bodySmall.fontFamily
                )
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Batal")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick
                ) {
                    Text(text = "Hapus")
                }
            }
        )
    }
}