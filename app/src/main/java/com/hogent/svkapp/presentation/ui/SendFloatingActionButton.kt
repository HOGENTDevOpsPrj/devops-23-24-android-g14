package com.hogent.svkapp.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun SendFloatingActionButton(onSend: () -> Unit, modifier: Modifier = Modifier) {
    ExtendedFloatingActionButton(onClick = onSend, icon = {
        Icon(
            imageVector = Icons.Default.Send, contentDescription = "Versturen"
        )
    }, text = { Text(text = "Versturen") }, modifier = modifier)
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SendFloatingActionButtonPreview() {
    TemplateApplicationTheme {
        SendFloatingActionButton(onSend = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SendFloatingActionButtonPreviewDark() = SendFloatingActionButtonPreview()

