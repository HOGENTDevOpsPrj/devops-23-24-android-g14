package com.hogent.svkapp.presentation.ui.images

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

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

val previewImagesList = listOf(
    Image.ResourceImage(resourceId = R.drawable.resource_default),
    Image.ResourceImage(resourceId = R.drawable.resource_default),
    Image.ResourceImage(resourceId = R.drawable.resource_default),
    Image.ResourceImage(resourceId = R.drawable.resource_default),
    Image.ResourceImage(resourceId = R.drawable.resource_default),
)

@Composable
fun ScrollableImageListPreviewBase(imageList: List<Image>) {
    TemplateApplicationTheme {
        ScrollableImageList(imageList = imageList, onDeleteImage = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ScrollableImageListFullPreview() {
    ScrollableImageListPreviewBase(imageList = previewImagesList)
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScrollableImageListFullPreviewDark() {
    ScrollableImageListPreviewBase(imageList = previewImagesList)
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ScrollableImageListEmptyPreview() {
    ScrollableImageListPreviewBase(imageList = emptyList())
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScrollableImageListEmptyPreviewDark() {
    ScrollableImageListPreviewBase(imageList = emptyList())
}