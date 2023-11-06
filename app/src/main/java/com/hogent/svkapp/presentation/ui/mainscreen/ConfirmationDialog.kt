package com.hogent.svkapp.presentation.ui.mainscreen

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * A dialog that is shown when the user has successfully sent a cargo ticket.
 *
 * @param onDismissRequest a callback that is called when the user dismisses the dialog.
 * @param modifier the modifier to apply to the dialog.
 *
 * @sample ConfirmationDialogPreview
 * @sample ConfirmationDialogPreviewDark
 */
@Composable
fun ConfirmationDialog(onDismissRequest: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(icon = {
        Icon(
            Icons.Filled.Check,
            contentDescription = stringResource(R.string.sent_confirmation_message_title)
        )
    }, title = {
        Text(text = stringResource(R.string.sent_confirmation_message_title))
    }, text = {
        Text(text = stringResource(R.string.sent_confirmation_message_body))
    }, onDismissRequest = {
        onDismissRequest()
    }, confirmButton = {
        TextButton(onClick = {
            onDismissRequest()
        }) {
            Text(stringResource(R.string.ok))
        }
    }, modifier = modifier
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ConfirmationDialogPreview() {
    TemplateApplicationTheme {
        ConfirmationDialog(onDismissRequest = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ConfirmationDialogPreviewDark(): Unit = ConfirmationDialogPreview()
