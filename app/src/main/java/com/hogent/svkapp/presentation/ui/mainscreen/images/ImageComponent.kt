package com.hogent.svkapp.presentation.ui.mainscreen.images

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing

/**
 * A composable that displays an [Image].
 *
 * @param image the [Image] to display.
 * @param modifier the modifier for this composable.
 *
 * @sample ImageComponentPreview
 * @sample ImageComponentPreviewDark
 */
@Composable
fun ImageComponent(image: Image, modifier: Modifier = Modifier) {
    val painter = BitmapPainter(image = image.bitmap.asImageBitmap())

    Image(
        painter = painter,
        contentDescription = stringResource(R.string.image_description),
        modifier = modifier
            .height(200.dp)
            .clip(shape = RoundedCornerShape(MaterialTheme.spacing.medium)),
        contentScale = ContentScale.Crop
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ImageComponentPreview() {
    TemplateApplicationTheme {
        ImageComponent(
            Image(
                bitmap = android.graphics.Bitmap.createBitmap(
                    100, 100, android.graphics.Bitmap.Config.ARGB_8888
                )
            )
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ImageComponentPreviewDark() = ImageComponentPreview()
