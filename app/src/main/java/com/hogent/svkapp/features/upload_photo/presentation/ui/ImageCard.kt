package com.hogent.svkapp.features.upload_photo.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hogent.svkapp.R
import com.hogent.svkapp.features.upload_photo.domain.ImageResource
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun ImageCard(imageResource: ImageResource, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = imageResource.imageResourceId),
        contentDescription = null,
        modifier = modifier
            .width(120.dp)
            .height(205.dp)
            .clip(RoundedCornerShape(28.dp)),
        contentScale = ContentScale.Crop,
        )
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ImageCardPreview() {
    TemplateApplicationTheme {
        ImageCard (ImageResource(R.drawable.resource_default))
    }
}