package com.hogent.svkapp.presentation.ui.images

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing

@Composable
fun ImageCard(image: Image, onDelete: () -> Unit, modifier: Modifier = Modifier) {
    val isAlertDialogOpen = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        RemoveImageDialog(isOpen = isAlertDialogOpen.value,
            onDismissRequest = { isAlertDialogOpen.value = false },
            onConfirmation = {
                onDelete()
                isAlertDialogOpen.value = false
            })

        RemoveImageButton(
            onClick = { isAlertDialogOpen.value = true },
            modifier = Modifier
                .padding(MaterialTheme.spacing.extraSmall)
                .zIndex(1f)
        )

        ImageComponent(image = image)
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ImageCardPreview() {
    TemplateApplicationTheme {
        ImageCard(Image.ResourceImage(resourceId = R.drawable.resource_default), onDelete = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageCardPreviewDark() = ImageCardPreview()
