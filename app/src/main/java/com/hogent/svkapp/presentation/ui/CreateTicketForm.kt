package com.hogent.svkapp.presentation.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.images.ScrollableImageList
import com.hogent.svkapp.presentation.ui.images.UploadImageButton
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing

@Composable
fun CreateTicketForm(
    routeNumber: String,
    licensePlate: String,
    onRouteNumberChange: (String) -> Unit,
    onLicensePlateChange: (String) -> Unit,
    routeNumberError: String?,
    licensePlateError: String?,
    images: List<Image>,
    onAddImage: (Image) -> Unit,
    onDeleteImage: (Image) -> Unit
) {
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            onAddImage(Image.BitmapImage(bitmap))
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium)
    ) {
        TicketTextField(
            value = routeNumber,
            label = "Routenummer",
            onValueChange = onRouteNumberChange,
            error = routeNumberError,
            keyboardType = KeyboardType.Number
        )
        TicketTextField(
            value = licensePlate,
            label = "Nummerplaat",
            onValueChange = onLicensePlateChange,
            error = licensePlateError,
            keyboardType = KeyboardType.Text
        )
        ScrollableImageList(imageList = images, onDeleteImage = onDeleteImage)
        UploadImageButton(onClick = { takePictureLauncher.launch(null) })
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketFormPreview() {
    TemplateApplicationTheme {
        CreateTicketForm(routeNumber = "123",
            licensePlate = "1-ABC-123",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = null,
            licensePlateError = null,
            images = listOf(),
            onAddImage = {},
            onDeleteImage = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketFormPreviewError() {
    TemplateApplicationTheme {
        CreateTicketForm(routeNumber = "",
            licensePlate = "",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = "Routenummer is ongeldig.",
            licensePlateError = "Gelieve een nummerplaat in te geven.",
            images = listOf(),
            onAddImage = {},
            onDeleteImage = {})
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketFormPreviewErrorRouteNumber() {
    TemplateApplicationTheme {
        CreateTicketForm(routeNumber = "",
            licensePlate = "1-ABC-123",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = "Routenummer is ongeldig.",
            licensePlateError = null,
            images = listOf(),
            onAddImage = {},
            onDeleteImage = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketFormPreviewDark() {
    TemplateApplicationTheme {
        CreateTicketForm(routeNumber = "123",
            licensePlate = "1-ABC-123",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = null,
            licensePlateError = null,
            images = listOf(),
            onAddImage = {},
            onDeleteImage = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketFormPreviewErrorDark() {
    TemplateApplicationTheme {
        CreateTicketForm(routeNumber = "",
            licensePlate = "",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = "Routenummer is ongeldig.",
            licensePlateError = "Gelieve een nummerplaat in te geven.",
            images = listOf(),
            onAddImage = {},
            onDeleteImage = {})
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketFormPreviewErrorRouteNumberDark() {
    TemplateApplicationTheme {
        CreateTicketForm(routeNumber = "",
            licensePlate = "1-ABC-123",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = "Routenummer is ongeldig.",
            licensePlateError = null,
            images = listOf(),
            onAddImage = {},
            onDeleteImage = {})
    }
}
