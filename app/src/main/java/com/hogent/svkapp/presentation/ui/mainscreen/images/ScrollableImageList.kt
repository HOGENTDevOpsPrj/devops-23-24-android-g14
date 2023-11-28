package com.hogent.svkapp.presentation.ui.mainscreen.images

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

/**
 * A scrollable list of [Image]s.
 *
 * @param imageList the list of [Image]s to display.
 * @param onDeleteImage the action to perform when an image is deleted.
 * @param modifier the modifier for this composable.
 *
 * @sample ScrollableImageListFullPreview
 * @sample ScrollableImageListFullPreviewDark
 * @sample ScrollableImageListEmptyPreview
 * @sample ScrollableImageListEmptyPreviewDark
 */
@Composable
fun ScrollableImageList(
    mainScreenViewModel: MainScreenViewModel,
    modifier: Modifier = Modifier
) {
    val mainScreenState by mainScreenViewModel.uiState.collectAsState()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small), modifier = modifier
    ) {
        items(mainScreenState.imageCollection) { image ->
            ImageCard(image = image, onDelete = { mainScreenViewModel.removeImage(image) })
        }
    }
}

private val previewImagesList =
    List(5) { Image(bitmap = android.graphics.Bitmap.createBitmap(100, 100, android.graphics.Bitmap.Config.ARGB_8888)) }

//@Composable
//private fun ScrollableImageListPreviewBase(imageList: List<Image>) {
//    TemplateApplicationTheme {
//        ScrollableImageList(imageList = imageList, onDeleteImage = {})
//    }
//}
//
//@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun ScrollableImageListFullPreview() = ScrollableImageListPreviewBase(previewImagesList)
//
//@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun ScrollableImageListFullPreviewDark() = ScrollableImageListPreviewBase(previewImagesList)
//
//@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun ScrollableImageListEmptyPreview() = ScrollableImageListPreviewBase(emptyList())
//
//@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun ScrollableImageListEmptyPreviewDark() = ScrollableImageListPreviewBase(emptyList())
