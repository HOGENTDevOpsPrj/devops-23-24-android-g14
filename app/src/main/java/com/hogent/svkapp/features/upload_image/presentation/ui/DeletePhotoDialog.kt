package com.hogent.svkapp.features.upload_image.presentation.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeletePhotoDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,) {
    AlertDialog(
        title = {
            Text(text = "Wilt u zeker deze foto verwijderen?")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Ja")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Neen")
            }
        }
    )

}