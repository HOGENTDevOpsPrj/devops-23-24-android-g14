package com.hogent.svkapp.features.create_ticket.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun TicketTextFieldPreview() {
    TemplateApplicationTheme(useDarkTheme = false) {
        TicketTextField("Sample", "Label") {}
    }
}
