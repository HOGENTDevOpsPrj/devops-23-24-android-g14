package com.hogent.svkapp.presentation.ui.images

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing

@Composable
fun ScrollableImageList(
    imageList: List<Image>, onDeleteImage: (Image) -> Unit, modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        modifier = modifier
    ) {
        items(imageList) { image ->
            ImageCard(image = image, onDelete = { onDeleteImage(image) })
        }
    }
}

val previewImagesList = List(5) { Image.ResourceImage(resourceId = R.drawable.resource_default) }

@Composable
fun ScrollableImageListPreviewBase(imageList: List<Image>) {
    TemplateApplicationTheme {
        ScrollableImageList(imageList = imageList, onDeleteImage = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ScrollableImageListFullPreview() = ScrollableImageListPreviewBase(previewImagesList)

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScrollableImageListFullPreviewDark() = ScrollableImageListPreviewBase(previewImagesList)

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ScrollableImageListEmptyPreview() = ScrollableImageListPreviewBase(emptyList())

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScrollableImageListEmptyPreviewDark() = ScrollableImageListPreviewBase(emptyList())
