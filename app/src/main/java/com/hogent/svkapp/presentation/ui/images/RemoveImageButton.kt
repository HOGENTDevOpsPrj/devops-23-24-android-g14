package com.hogent.svkapp.presentation.ui.images

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

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
            Icons.Default.Clear, contentDescription = "Remove Image"
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun RemoveImageButtonPreview() {
    TemplateApplicationTheme {
        RemoveImageButton(onClick = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RemoveImageButtonPreviewDark() = RemoveImageButtonPreview()
