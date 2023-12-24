package com.hogent.svkapp.presentation.ui.mainscreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.presentation.ui.qrScanner.QrScanButton
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * A custom text field for this app.
 *
 * @param modifier The modifier to be applied to the text field.
 * @param value The value of the text field.
 * @param label The label of the text field.
 * @param onValueChange The callback to be invoked when the value of the text field changes.
 * @param errors The error messages to be displayed below the text field.
 * @param keyboardType The type of keyboard to be used for the text field.
 *
 * @sample TextFieldPreview
 * @sample TextFieldPreviewDark
 * @sample InvalidTextFieldPreview
 * @sample InvalidTextFieldPreviewDark
 */
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    index: Int,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    onDelete: () -> Unit,
    errors: List<String?>? = emptyList(),
    navigateToQrScanner: (Int) -> Unit,
    keyboardType: KeyboardType,
    removable: Boolean = true,
    scannable: Boolean = false,
    onRequestPermission: () -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        supportingText = {
            Column {
                errors?.forEach { error ->
                    if (error != null) {
                        Text(text = error)
                    }
                }
            }
        },
        isError = !errors.isNullOrEmpty(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        modifier = modifier,
        trailingIcon = {
            Row {
                if (!errors.isNullOrEmpty()) {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(
                            Icons.Filled.Warning,
                            contentDescription = stringResource(R.string.error_icon_content_description)
                        )
                    }
                }
                if (scannable) {
                    QrScanButton(
                        navigateToQrScanner = {
                            onRequestPermission()
                            navigateToQrScanner(index)
                        },
                    )
                }
                if (removable) {
                    IconButton(
                        onClick = { onDelete() },
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onSurface,
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(
                                R.string.remove_route_number_button_content_description, index + 1
                            )
                        )
                    }
                }
            }
        }
    )
}


@Composable
private fun TextFieldPreviewBase(
    value: String = "",
    label: String = stringResource(R.string.custom_text_field_previews_label_text),
    errors: List<String?> = emptyList(),
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TemplateApplicationTheme {
        CustomTextField(
            index = 0,
            value = value,
            label = label,
            onValueChange = {},
            onDelete = {},
            errors = errors,
            navigateToQrScanner = {},
            keyboardType = keyboardType,
            scannable = true,
            onRequestPermission = { },
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
    errors = listOf(
        stringResource(id = R.string.empty_route_number_error_message),
        stringResource(id = R.string.invalid_route_number_error_message),
    )
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun InvalidTextFieldPreviewDark(): Unit = InvalidTextFieldPreview()
