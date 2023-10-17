package com.hogent.svkapp.features.create_ticket.presentation.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.features.create_ticket.domain.entities.Image
import com.hogent.svkapp.features.create_ticket.presentation.ui.images.ScrollableImageList
import com.hogent.svkapp.features.create_ticket.presentation.ui.images.UploadImageButton
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.main.presentation.ui.theme.spacing

@Composable
fun CreateTicketForm(
    routeNumber: String,
    licensePlate: String,
    images: List<Image>,
    onRouteNumberChange: (String) -> Unit,
    onLicensePlateChange: (String) -> Unit,
    onAddImage: (Image) -> Unit,
    routeNumberError: String?,
    licensePlateError: String?,
    imagesError: String?,
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
        ScrollableImageList(imageList = images)
        if (imagesError != null) {
            Text(
                text = imagesError,
                modifier = Modifier.padding(start = MaterialTheme.spacing.medium)
            )
        }
        UploadImageButton(onClick = { takePictureLauncher.launch(null) })
    }
}

@Composable
fun CreateTicketFormPreviewBase(
    routeNumber: String,
    licensePlate: String,
    images: List<Image>,
    routeNumberError: String?,
    licensePlateError: String?,
    imagesError: String?,
) {
    TemplateApplicationTheme {
        CreateTicketForm(
            routeNumber = routeNumber,
            licensePlate = licensePlate,
            images = images,
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            onAddImage = {},
            routeNumberError = routeNumberError,
            licensePlateError = licensePlateError,
            imagesError = imagesError,
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketFormPreview() {
    CreateTicketFormPreviewBase(
        routeNumber = "123",
        licensePlate = "1-ABC-123",
        images = listOf(),
        routeNumberError = null,
        licensePlateError = null,
        imagesError = null,
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketFormPreviewError() {
    CreateTicketFormPreviewBase(
        routeNumber = "",
        licensePlate = "",
        images = listOf(),
        routeNumberError = "Routenummer is ongeldig.",
        licensePlateError = "Gelieve een nummerplaat in te geven.",
        imagesError = "Gelieve minstens 1 foto toe te voegen.",
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketFormPreviewErrorRouteNumber() {
    CreateTicketFormPreviewBase(
        routeNumber = "",
        licensePlate = "1-ABC-123",
        images = listOf(),
        routeNumberError = "Routenummer is ongeldig.",
        licensePlateError = null,
        imagesError = null,
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketFormPreviewDark() {
    CreateTicketFormPreviewBase(
        routeNumber = "123",
        licensePlate = "1-ABC-123",
        images = listOf(),
        routeNumberError = null,
        licensePlateError = null,
        imagesError = null,
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketFormPreviewErrorDark() {
    CreateTicketFormPreviewBase(
        routeNumber = "",
        licensePlate = "",
        images = listOf(),
        routeNumberError = "Routenummer is ongeldig.",
        licensePlateError = "Gelieve een nummerplaat in te geven.",
        imagesError = "Gelieve minstens 1 foto toe te voegen.",
    )
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketFormPreviewErrorRouteNumberDark() {
    CreateTicketFormPreviewBase(
        routeNumber = "",
        licensePlate = "1-ABC-123",
        images = listOf(),
        routeNumberError = "Routenummer is ongeldig.",
        licensePlateError = null,
        imagesError = null,
    )
}
