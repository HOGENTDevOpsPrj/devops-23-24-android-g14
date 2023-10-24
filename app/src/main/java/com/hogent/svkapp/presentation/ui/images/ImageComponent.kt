package com.hogent.svkapp.presentation.ui.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing

@Composable
fun ImageComponent(image: Image, modifier: Modifier = Modifier) {
    val painter = getPainter(image)

    Image(
        painter = painter,
        contentDescription = "Image you took.",
        modifier = modifier
            .height(200.dp)
            .clip(shape = RoundedCornerShape(MaterialTheme.spacing.medium)),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun getPainter(image: Image) = when (image) {
    is Image.BitmapImage -> BitmapPainter(image = image.bitmap.asImageBitmap())
    is Image.ResourceImage -> painterResource(id = image.resourceId)
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ImageComponentPreview() {
    TemplateApplicationTheme {
        ImageComponent(Image.ResourceImage(resourceId = com.hogent.svkapp.R.drawable.resource_default))
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageComponentPreviewDark() = ImageComponentPreview()
