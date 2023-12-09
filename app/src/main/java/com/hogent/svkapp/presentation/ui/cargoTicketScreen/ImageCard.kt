package com.hogent.svkapp.presentation.ui.cargoTicketScreen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: Image,
) {
    Image(
        painter = BitmapPainter(image = image.bitmap.asImageBitmap()),
        contentDescription = null,
        modifier = modifier
            .height(100.dp)
            .clip(shape = RoundedCornerShape(MaterialTheme.spacing.medium)),
    )
}

@Preview(showBackground = true)
@Composable
fun ImageCardPreview() {
    TemplateApplicationTheme {
        ImageCard(
            image = Image(
                bitmap = Bitmap.createBitmap(100, 100, android.graphics.Bitmap.Config.ARGB_8888)
            )
        )
    }
}