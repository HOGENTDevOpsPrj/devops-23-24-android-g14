package com.hogent.svkapp.features.create_ticket.presentation.ui

import MockImageSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.features.upload_photo.domain.ImageResource
import com.hogent.svkapp.features.upload_photo.presentation.ui.UploadPhotoForm
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.main.presentation.ui.theme.spacing

@Composable
fun CreateTicketForm(
    routeNumber: String,
    licensePlate: String,
    onRouteNumberChange: (String) -> Unit,
    onLicensePlateChange: (String) -> Unit,
    routeNumberError: String?,
    licensePlateError: String?,
    images: List<ImageResource>
) {
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
        UploadPhotoForm(images)
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketFormPreview() {
    TemplateApplicationTheme {
        CreateTicketForm(
            routeNumber = "123",
            licensePlate = "1-ABC-123",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = null,
            licensePlateError = null,
            images = MockImageSource().loadImages(),
        )
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketFormPreviewError() {
    TemplateApplicationTheme {
        CreateTicketForm(
            routeNumber = "",
            licensePlate = "",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = "Routenummer is ongeldig.",
            licensePlateError = "Gelieve een nummerplaat in te geven.",
            images = MockImageSource().loadImages(),
        )
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketFormPreviewErrorRouteNumber() {
    TemplateApplicationTheme {
        CreateTicketForm(
            routeNumber = "",
            licensePlate = "1-ABC-123",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = "Routenummer is ongeldig.",
            licensePlateError = null,
            images = MockImageSource().loadImages(),
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketFormPreviewDark() {
    TemplateApplicationTheme {
        CreateTicketForm(
            routeNumber = "123",
            licensePlate = "1-ABC-123",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = null,
            licensePlateError = null,
            images = MockImageSource().loadImages(),
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketFormPreviewErrorDark() {
    TemplateApplicationTheme {
        CreateTicketForm(
            routeNumber = "",
            licensePlate = "",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = "Routenummer is ongeldig.",
            licensePlateError = "Gelieve een nummerplaat in te geven.",
            images = MockImageSource().loadImages(),
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketFormPreviewErrorRouteNumberDark() {
    TemplateApplicationTheme {
        CreateTicketForm(
            routeNumber = "",
            licensePlate = "1-ABC-123",
            onRouteNumberChange = {},
            onLicensePlateChange = {},
            routeNumberError = "Routenummer is ongeldig.",
            licensePlateError = null,
            images = MockImageSource().loadImages(),
        )
    }
}