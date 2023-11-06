package com.hogent.svkapp.presentation.ui.mainscreen.images

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * A dialog that asks the user if they want to remove an image.
 *
 * @param isOpen whether the dialog is open.
 * @param onDismissRequest the action to perform when the dialog is dismissed.
 * @param onConfirmation the action to perform when the user confirms the removal.
 * @param modifier the modifier for this composable.
 *
 * @sample RemoveImageDialogPreview
 * @sample RemoveImageDialogPreviewDark
 */
@Composable
fun RemoveImageDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isOpen) {
        AlertDialog(
            title = { Text(stringResource(R.string.remove_image_confirmation_dialog_title)) },
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onConfirmation) {
                    Text(stringResource(R.string.remove_image_confirmation_dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(stringResource(R.string.remove_image_confirmation_dialog_dismiss))
                }
            },
            modifier = modifier
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun RemoveImageDialogPreview() {
    TemplateApplicationTheme {
        RemoveImageDialog(isOpen = true, onDismissRequest = {}, onConfirmation = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RemoveImageDialogPreviewDark() = RemoveImageDialogPreview()
