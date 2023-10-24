package com.hogent.svkapp.presentation.ui.mainscreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.images.AddImageButton
import com.hogent.svkapp.presentation.ui.images.ScrollableImageList
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing

@Composable
fun Form(
    routeNumber: String,
    licensePlate: String,
    images: List<Image>,
    routeNumberError: String?,
    licensePlateError: String?,
    imagesError: String?,
    onRouteNumberChange: (String) -> Unit,
    onLicensePlateChange: (String) -> Unit,
    onAddImage: (Image) -> Unit,
    onRemoveImage: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    val takePictureLauncher = getTakePictureLauncher(onAddImage)

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
    ) {
        TextField(
            value = routeNumber,
            label = "Routenummer",
            onValueChange = onRouteNumberChange,
            error = routeNumberError,
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth(),
        )
        TextField(
            value = licensePlate,
            label = "Nummerplaat",
            onValueChange = onLicensePlateChange,
            error = licensePlateError,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth(),
        )
        if (imagesError != null) {
            Text(text = imagesError, color = MaterialTheme.colorScheme.error)
        } else {
            ScrollableImageList(imageList = images, onDeleteImage = onRemoveImage)
        }
        AddImageButton(
            onClick = { takePictureLauncher.launch(null) }, modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun getTakePictureLauncher(onAddImage: (Image) -> Unit) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.TakePicturePreview()
) { bitmap ->
    bitmap?.let {
        onAddImage(Image.BitmapImage(bitmap))
    }
}

@Composable
fun PreviewWrapper(
    routeNumber: String = "",
    licensePlate: String = "",
    images: List<Image> = listOf(),
    routeNumberError: String? = null,
    licensePlateError: String? = null,
    imageError: String? = null
) {
    TemplateApplicationTheme {
        Form(routeNumber = routeNumber,
            licensePlate = licensePlate,
            images = images,
            routeNumberError = routeNumberError,
            licensePlateError = licensePlateError,
            imagesError = imageError,
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            onAddImage = {},
            onRemoveImage = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun FormPreview() = PreviewWrapper()

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FormPreviewDark() = FormPreview()

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun FormPreviewWithErrors() = PreviewWrapper(
    routeNumberError = "Routenummer is verplicht",
    licensePlateError = "Nummerplaat is verplicht",
    imageError = "Foto is verplicht"
)

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FormPreviewWithErrorsDark() = FormPreviewWithErrors()

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun FormPreviewWithData() = PreviewWrapper(routeNumber = "123",
    licensePlate = "ABC-123",
    images = List(5) { Image.ResourceImage(resourceId = R.drawable.resource_default) })

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FormPreviewWithDataDark() = FormPreviewWithData()

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun FormPreviewWithDataAndErrors() = PreviewWrapper(
    routeNumber = "dit is geen nummer",
    licensePlate = "dit is geen nummerplaat",
    images = List(5) { Image.ResourceImage(resourceId = R.drawable.resource_default) },
    routeNumberError = "Routenummer is verplicht",
    licensePlateError = "Nummerplaat is verplicht",
    imageError = "Foto is verplicht"
)

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FormPreviewWithDataAndErrorsDark() = FormPreviewWithDataAndErrors()
