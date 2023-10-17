package com.hogent.svkapp.features.create_ticket.presentation.ui.images

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.features.create_ticket.domain.entities.Image
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun ScrollableImageList(imageList: List<Image>) {
    LazyRow {
        items(items = imageList) { imageRes ->
            ImageCard(image = imageRes)
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
        ScrollableImageList(imageList = imageList)
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
