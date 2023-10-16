package com.hogent.svkapp.features.upload_image.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.hogent.svkapp.R
import com.hogent.svkapp.features.upload_image.domain.Image
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.main.presentation.ui.theme.spacing

@Composable
fun ImageCard(image: Image) {
    val painter = when {
        image.bitmap != null -> BitmapPainter(image = image.bitmap.asImageBitmap())
        image.resourceId != null -> painterResource(id = image.resourceId)
        else -> throw IllegalArgumentException("ImageResource must have either a bitmap or a resourceId")
    }

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .padding(MaterialTheme.spacing.small)
            .width(120.dp)
            .height(205.dp)
            .clip(RoundedCornerShape(MaterialTheme.spacing.medium)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ImageCardPreviewBase() {
    TemplateApplicationTheme {
        ImageCard(image = Image(resourceId = R.drawable.resource_default))
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ImageCardPreview() {
    ImageCardPreviewBase()
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageCardPreviewDark() {
    ImageCardPreviewBase()
}
