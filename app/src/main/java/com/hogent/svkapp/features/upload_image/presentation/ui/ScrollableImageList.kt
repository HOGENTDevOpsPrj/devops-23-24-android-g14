package com.hogent.svkapp.features.upload_image.presentation.ui

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.features.upload_image.domain.Image
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun ScrollableImageList(imageList: List<Image>, onDeleteImage: (Image) -> Unit) {

    LazyRow {
        items(imageList) { imageRes ->


            ImageCard(image = imageRes, onDelete = {
                onDeleteImage(imageRes)
            })
        }
    }
}


@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ScrollableImageListPreview() {
    TemplateApplicationTheme {
        ScrollableImageList(
            imageList = mutableListOf(),
            onDeleteImage = {},
            )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScrollableImageListPreviewDark() {
    TemplateApplicationTheme {
        ScrollableImageList(imageList = mutableListOf(),
            onDeleteImage = {},
            )
    }
}

