package com.hogent.svkapp.features.upload_photo.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun UploadPhotoButton() {
    Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
        Text("Voeg foto toe")
    }
}



@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UploadPhotoButtonPreview() {
    TemplateApplicationTheme {
        UploadPhotoButton ()
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UploadPhotoButtonPreviewDark() {
    TemplateApplicationTheme {
        UploadPhotoButton ()
    }
}