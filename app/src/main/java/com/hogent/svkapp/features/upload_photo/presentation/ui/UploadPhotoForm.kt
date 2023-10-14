package com.hogent.svkapp.features.upload_photo.presentation.ui

import MockImageSource
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.features.upload_photo.domain.ImageResource
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun UploadPhotoForm(imageList: List<ImageResource>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ScrollableImageList(imageList = imageList)
        UploadPhotoButton()
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UploadPhotoFormPreviewEmpty() {
    TemplateApplicationTheme {
        UploadPhotoForm(imageList = emptyList())
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UploadPhotoFormPreviewEmptyDark() {
    TemplateApplicationTheme {
        UploadPhotoForm(imageList = emptyList())
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UploadPhotoFormPreview() {
    TemplateApplicationTheme {
        UploadPhotoForm(imageList = MockImageSource().loadImages())
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UploadPhotoFormPreviewDark() {
    TemplateApplicationTheme {
        UploadPhotoForm(imageList = MockImageSource().loadImages())
    }
}
