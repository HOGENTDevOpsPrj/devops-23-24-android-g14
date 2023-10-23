package com.hogent.svkapp.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

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
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        supportingText = {
            if (error != null) {
                Text(text = error)
            }
        },
        isError = error != null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true
    )
}

@Composable
fun TicketTextFieldPreviewBase(
    value: String, label: String, error: String? = null
) {
    TemplateApplicationTheme {
        TicketTextField(
            value = value,
            label = label,
            onValueChange = {},
            error = error,
            keyboardType = KeyboardType.Text
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ValidTicketTextFieldPreview() {
    TicketTextFieldPreviewBase(
        value = "1-ABC-123", label = "Nummerplaat", error = null
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun InvalidTicketTextFieldPreview() {
    TicketTextFieldPreviewBase(
        value = "", label = "Nummerplaat", error = "Gelieve een nummerplaat in te geven."
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ValidTicketTextFieldPreviewDark() {
    TicketTextFieldPreviewBase(
        value = "1-ABC-123", label = "Nummerplaat", error = null
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InvalidTicketTextFieldPreviewDark() {
    TicketTextFieldPreviewBase(
        value = "", label = "Nummerplaat", error = "Gelieve een nummerplaat in te geven."
    )
}
