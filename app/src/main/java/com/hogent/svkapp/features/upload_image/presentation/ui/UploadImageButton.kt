package com.hogent.svkapp.features.upload_image.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun UploadImageButton(onClick: () -> Unit = {}) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text("Voeg foto toe")
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UploadImageButtonPreview() {
    TemplateApplicationTheme {
        UploadImageButton()
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UploadImageButtonPreviewDark() {
    TemplateApplicationTheme {
        UploadImageButton()
    }
}