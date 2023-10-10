package com.hogent.svkapp.features.create_ticket.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun SendFloatingActionButton(onSend: () -> Unit) {
    LargeFloatingActionButton(onClick = onSend) {
        Icon(Icons.Default.Send, "Large floating action button")
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SendFloatingActionButtonPreview() {
    TemplateApplicationTheme {
        SendFloatingActionButton {}
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SendFloatingActionButtonPreviewDark() {
    TemplateApplicationTheme {
        SendFloatingActionButton {}
    }
}
