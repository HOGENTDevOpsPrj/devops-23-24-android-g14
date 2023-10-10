package com.hogent.svkapp.features.create_ticket.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    keyboardType: KeyboardType
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        supportingText = {
            if (error != null) {
                Text(text = error)
            }
        },
        isError = error != null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ValidTicketTextFieldPreview() {
    TemplateApplicationTheme {
        TicketTextField(
            value = "1-ABC-123",
            label = "Nummerplaat",
            onValueChange = {},
            error = null,
            keyboardType = KeyboardType.Text
        )
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun InvalidTicketTextFieldPreview() {
    TemplateApplicationTheme {
        TicketTextField(
            value = "",
            label = "Nummerplaat",
            onValueChange = {},
            error = "Gelieve een nummerplaat in te geven.",
            keyboardType = KeyboardType.Text
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ValidTicketTextFieldPreviewDark() {
    TemplateApplicationTheme {
        TicketTextField(
            value = "1-ABC-123",
            label = "Nummerplaat",
            onValueChange = {},
            error = null,
            keyboardType = KeyboardType.Text
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InvalidTicketTextFieldPreviewDark() {
    TemplateApplicationTheme {
        TicketTextField(
            value = "",
            label = "Nummerplaat",
            onValueChange = {},
            error = "Gelieve een nummerplaat in te geven.",
            keyboardType = KeyboardType.Text
        )
    }
}
