package com.hogent.svkapp.presentation.ui.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.features.upload_image.presentation.ui.DeletePhotoDialog
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

@Composable
fun ImageCard(image: Image, onDelete: () -> Unit) {
    val painter = when (image) {
        is Image.BitmapImage -> BitmapPainter(image.bitmap.asImageBitmap())
        is Image.ResourceImage -> painterResource(id = image.resourceId)
    }

    val openAlertDialog = remember { mutableStateOf(false) }

    Box {
        when {
            openAlertDialog.value -> {
                DeletePhotoDialog(onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        onDelete()
                        openAlertDialog.value = false
                    })
            }
        }

        Button(
            onClick = { openAlertDialog.value = true },
            shape = CircleShape,
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)
                .zIndex(1f),
            contentPadding = PaddingValues(0.dp),
        ) {
            Icon(
                Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.75f)
            )
        }
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .width(120.dp)
                .height(205.dp)
                .clip(RoundedCornerShape(28.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ImageCardPreviewBase() {
    TemplateApplicationTheme {
        ImageCard(Image.ResourceImage(resourceId = R.drawable.resource_default), onDelete = {})
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