package com.hogent.svkapp.features.upload_image.presentation.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.features.upload_image.domain.Image
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun UploadImageForm(
    images: List<Image>,
    onAddImage: (Image) -> Unit,
    onDeleteImage: (Image) -> Unit) {

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            onAddImage(Image(bitmap))
        }
    }

    Column {
        ScrollableImageList(imageList = images, onDeleteImage = onDeleteImage)
        UploadImageButton(onClick = { takePictureLauncher.launch(null) })
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UploadPhotoFormPreviewEmpty() {
    TemplateApplicationTheme {
        UploadImageForm(
            images = mutableListOf(),
            onAddImage = {},
            onDeleteImage = {},
            )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UploadPhotoFormPreviewEmptyDark() {
    TemplateApplicationTheme {
        UploadImageForm(
            images = mutableListOf(),
            onAddImage = {},
            onDeleteImage = {},
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UploadPhotoFormPreview() {
    TemplateApplicationTheme {
        UploadImageForm(
            images = mutableListOf(),
            onAddImage = {},
            onDeleteImage = {},
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UploadPhotoFormPreviewDark() {
    TemplateApplicationTheme {
        UploadImageForm(
            images = mutableListOf(),
            onAddImage = {},
            onDeleteImage = {},
        )
    }
}
