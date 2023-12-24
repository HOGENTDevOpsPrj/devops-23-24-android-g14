package com.hogent.svkapp.presentation.ui.cargoTicketScreen

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hogent.svkapp.domain.entities.Image

/**
 * A scrollable list of images.
 */
@Composable
fun ScrollableImageList(
    modifier: Modifier = Modifier,
    images: List<Image>,
) {
    LazyRow(modifier = modifier) {
        items(images) { image ->
            ImageCard(image = image)
        }
    }
}