package com.hogent.svkapp.presentation.ui.mainscreen.images

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * A button that adds an image.
 *
 * @param onClick the action to perform when the button is clicked.
 * @param modifier the modifier for this composable.
 *
 * @sample AddImageButtonPreview
 * @sample AddImageButtonPreviewDark
 */
@Composable
fun AddImageButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text(stringResource(R.string.add_image_button_text))
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AddImageButtonPreview() {
    TemplateApplicationTheme {
        AddImageButton(onClick = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddImageButtonPreviewDark() = AddImageButtonPreview()
