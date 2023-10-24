package com.hogent.svkapp.presentation.ui.images

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun AddImageButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text("Voeg foto toe")
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AddImageButtonPreview() {
    TemplateApplicationTheme {
        AddImageButton(onClick = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddImageButtonPreviewDark() = AddImageButtonPreview()
