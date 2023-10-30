package com.hogent.svkapp.presentation.ui.mainscreen.images

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun RemoveImageDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isOpen) {
        AlertDialog(
            title = { Text("Foto verwijderen?") },
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onConfirmation) {
                    Text("Ja")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text("Neen")
                }
            },
            modifier = modifier
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun RemoveImageDialogPreview() {
    TemplateApplicationTheme {
        RemoveImageDialog(isOpen = true, onDismissRequest = {}, onConfirmation = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RemoveImageDialogPreviewDark() = RemoveImageDialogPreview()
