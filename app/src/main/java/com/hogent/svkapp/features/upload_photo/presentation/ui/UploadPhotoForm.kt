package com.hogent.svkapp.features.upload_photo.presentation.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.features.upload_photo.MockUploadPhotoModule
import com.hogent.svkapp.features.upload_photo.domain.Photo
import com.hogent.svkapp.features.upload_photo.presentation.viewmodel.UploadPhotoViewModel
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun UploadPhotoForm(viewModel: UploadPhotoViewModel, modifier: Modifier = Modifier) {
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            viewModel.addPhoto(Photo(bitmap = it))
        }
    }

    val photos = remember { viewModel.photos }

    Column(modifier = modifier) {
        ScrollableImageList(imageList = photos)
        UploadPhotoButton(onClick = { takePictureLauncher.launch(null) })
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UploadPhotoFormPreviewEmpty() {
    TemplateApplicationTheme {
        val mockUploadPhotoModule = MockUploadPhotoModule()
        UploadPhotoForm(mockUploadPhotoModule.getViewModel())
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UploadPhotoFormPreviewEmptyDark() {
    TemplateApplicationTheme {
        val mockUploadPhotoModule = MockUploadPhotoModule()
        UploadPhotoForm(mockUploadPhotoModule.getViewModel())
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UploadPhotoFormPreview() {
    TemplateApplicationTheme {
        val mockUploadPhotoModule = MockUploadPhotoModule()
        UploadPhotoForm(mockUploadPhotoModule.getViewModel())
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UploadPhotoFormPreviewDark() {
    TemplateApplicationTheme {
        val mockUploadPhotoModule = MockUploadPhotoModule()
        UploadPhotoForm(mockUploadPhotoModule.getViewModel())
    }
}
