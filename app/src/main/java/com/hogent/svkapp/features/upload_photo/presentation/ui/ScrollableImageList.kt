package com.hogent.svkapp.features.upload_photo.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hogent.svkapp.features.upload_photo.data.sources.LocalImageDataSource
import com.hogent.svkapp.features.upload_photo.domain.Photo
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun ScrollableImageList(imageList: List<Photo>, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        items(imageList) { imageRes ->
            ImageCard(
                photo = imageRes, modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ScrollableImageListPreview() {
    TemplateApplicationTheme {
        ScrollableImageList(imageList = LocalImageDataSource().loadImages())
    }

}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScrollableImageListPreviewDark() {
    TemplateApplicationTheme {
        ScrollableImageList(imageList = LocalImageDataSource().loadImages())
    }
}