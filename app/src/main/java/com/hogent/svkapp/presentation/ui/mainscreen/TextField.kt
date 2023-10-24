package com.hogent.svkapp.presentation.ui.mainscreen

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    keyboardType: KeyboardType,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        supportingText = {
            if (error != null) {
                Text(text = error)
            }
        },
        isError = error != null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        modifier = modifier,
    )
}

@Composable
fun TextFieldPreviewBase(
    value: String = "",
    label: String = "Label",
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TemplateApplicationTheme {
        TextField(
            value = value,
            label = label,
            onValueChange = {},
            error = error,
            keyboardType = keyboardType
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TextFieldPreview() = TextFieldPreviewBase(
    value = "1-ABC-123", label = "Nummerplaat"
)

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TextFieldPreviewDark() = TextFieldPreviewBase(
    value = "1-ABC-123", label = "Nummerplaat"
)

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun InvalidTextFieldPreview() = TextFieldPreviewBase(
    value = "1-ABC-123", label = "Nummerplaat", error = "Invalid"
)

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InvalidTextFieldPreviewDark() = TextFieldPreviewBase(
    value = "1-ABC-123", label = "Nummerplaat", error = "Invalid"
)
