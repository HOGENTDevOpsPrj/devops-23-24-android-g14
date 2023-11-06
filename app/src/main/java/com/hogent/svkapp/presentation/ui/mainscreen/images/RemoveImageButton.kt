package com.hogent.svkapp.presentation.ui.mainscreen.images

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * A button that removes an image.
 *
 * @param onClick the action to perform when the button is clicked.
 * @param modifier the modifier for this composable.
 *
 * @sample RemoveImageButtonPreview
 * @sample RemoveImageButtonPreviewDark
 */
@Composable
fun RemoveImageButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick, colors = IconButtonDefaults.filledIconButtonColors(
            contentColor = MaterialTheme.colorScheme.onError,
            containerColor = MaterialTheme.colorScheme.error,
            disabledContentColor = MaterialTheme.colorScheme.error.copy(alpha = 0.38f),
            disabledContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
        ), modifier = modifier
    ) {
        Icon(
            Icons.Default.Clear,
            contentDescription = stringResource(R.string.remove_image_button_content_description)
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun RemoveImageButtonPreview() {
    TemplateApplicationTheme {
        RemoveImageButton(onClick = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RemoveImageButtonPreviewDark() = RemoveImageButtonPreview()
