package com.hogent.svkapp.presentation.ui.mainscreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun ConfirmationDialog(onDismissRequest: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(icon = {
        Icon(Icons.Filled.Check, contentDescription = "Example Icon")
    }, title = {
        Text(text = "Verstuurd!")
    }, text = {
        Text(text = "De ladingsinformatie is verstuurd naar SVK.")
    }, onDismissRequest = {
        onDismissRequest()
    }, confirmButton = {
        TextButton(onClick = {
            onDismissRequest()
        }) {
            Text("OK")
        }
    }, modifier = modifier
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ConfirmationDialogPreview() {
    TemplateApplicationTheme {
        ConfirmationDialog(onDismissRequest = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ConfirmationDialogPreviewDark() = ConfirmationDialogPreview()
