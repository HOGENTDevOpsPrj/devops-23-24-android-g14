package com.hogent.svkapp.presentation.ui.mainscreen

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * A custom text field for this app.
 *
 * @param modifier The modifier to be applied to the text field.
 * @param value The value of the text field.
 * @param label The label of the text field.
 * @param onValueChange The callback to be invoked when the value of the text field changes.
 * @param error The error message to be displayed below the text field.
 * @param keyboardType The type of keyboard to be used for the text field.
 * @param trailingIcon The trailing icon to be displayed in the text field.
 *
 * @sample TextFieldPreview
 * @sample TextFieldPreviewDark
 * @sample InvalidTextFieldPreview
 * @sample InvalidTextFieldPreviewDark
 */
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    keyboardType: KeyboardType,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(value = value,
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
        trailingIcon = {
            if (error != null) {
                Icon(
                    Icons.Filled.Warning,
                    contentDescription = stringResource(R.string.error_icon_content_description)
                )
            } else {
                trailingIcon?.invoke()
            }
        })
}

@Composable
private fun TextFieldPreviewBase(
    value: String = "",
    label: String = stringResource(R.string.custom_text_field_previews_label_text),
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TemplateApplicationTheme {
        CustomTextField(
            value = value,
            label = label,
            onValueChange = {},
            error = error,
            keyboardType = keyboardType
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TextFieldPreview(): Unit = TextFieldPreviewBase(
    value = stringResource(R.string.previews_license_plate),
    label = stringResource(R.string.license_plate_label_text)
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TextFieldPreviewDark(): Unit = TextFieldPreview()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun InvalidTextFieldPreview(): Unit = TextFieldPreviewBase(
    value = stringResource(R.string.previews_license_plate),
    label = stringResource(R.string.license_plate_label_text),
    error = stringResource(R.string.invalid_license_plate_error_message)
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun InvalidTextFieldPreviewDark(): Unit = InvalidTextFieldPreview()
