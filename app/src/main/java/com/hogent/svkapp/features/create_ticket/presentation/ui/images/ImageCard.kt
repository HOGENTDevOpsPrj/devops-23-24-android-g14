package com.hogent.svkapp.features.create_ticket.presentation.ui.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
<<<<<<< HEAD:app/src/main/java/com/hogent/svkapp/features/create_ticket/presentation/ui/images/ImageCard.kt
import androidx.compose.material3.MaterialTheme
=======
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
>>>>>>> feature/upload-photo:app/src/main/java/com/hogent/svkapp/features/upload_image/presentation/ui/ImageCard.kt
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
import com.hogent.svkapp.features.create_ticket.domain.entities.Image
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.main.presentation.ui.theme.spacing

@Composable
<<<<<<< HEAD:app/src/main/java/com/hogent/svkapp/features/create_ticket/presentation/ui/images/ImageCard.kt
fun ImageCard(image: Image) {
    val painter = when (image) {
        is Image.BitmapImage -> BitmapPainter(image.bitmap.asImageBitmap())
        is Image.ResourceImage -> painterResource(id = image.resourceId)
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
        ImageCard(image = Image.ResourceImage(resourceId = R.drawable.resource_default))
    }
}
=======
fun ImageCard(image: Image, onDelete: () -> Unit) {
    val painter = when {
        image.bitmap != null -> BitmapPainter(image.bitmap.asImageBitmap())
        image.resourceId != null -> painterResource(id = image.resourceId)
        else -> throw IllegalArgumentException("ImageResource must have either a bitmap or a resourceId")
    }

    val openAlertDialog = remember { mutableStateOf(false) }

    Box {
        when {
            openAlertDialog.value -> {
                DeletePhotoDialog(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        onDelete()
                        openAlertDialog.value = false
                    })
            }
        }

        Button(
            onClick = {openAlertDialog.value = true},
            shape = CircleShape,
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)
                .zIndex(1f),
            contentPadding = PaddingValues(0.dp),
        ) {
            Icon(Icons.Default.Clear,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.75f)
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

>>>>>>> feature/upload-photo:app/src/main/java/com/hogent/svkapp/features/upload_image/presentation/ui/ImageCard.kt

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ImageCardPreview() {
<<<<<<< HEAD:app/src/main/java/com/hogent/svkapp/features/create_ticket/presentation/ui/images/ImageCard.kt
    ImageCardPreviewBase()
=======
    TemplateApplicationTheme {
        ImageCard(Image(resourceId = R.drawable.resource_default), onDelete = {})
    }
>>>>>>> feature/upload-photo:app/src/main/java/com/hogent/svkapp/features/upload_image/presentation/ui/ImageCard.kt
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageCardPreviewDark() {
<<<<<<< HEAD:app/src/main/java/com/hogent/svkapp/features/create_ticket/presentation/ui/images/ImageCard.kt
    ImageCardPreviewBase()
=======
    TemplateApplicationTheme {
        ImageCard(Image(resourceId = R.drawable.resource_default), onDelete = {})
    }
>>>>>>> feature/upload-photo:app/src/main/java/com/hogent/svkapp/features/upload_image/presentation/ui/ImageCard.kt
}
