package com.hogent.svkapp.features.create_ticket.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.main.presentation.ui.theme.spacing

@Composable
fun CreateTicketForm(
    routeNumber: String,
    licensePlate: String,
    onRouteNumberChange: (String) -> Unit,
    onLicensePlateChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        TicketTextField(
            value = routeNumber, label = "Routenummer", onValueChange = onRouteNumberChange
        )
        TicketTextField(
            value = licensePlate, label = "Nummerplaat", onValueChange = onLicensePlateChange
        )
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
            Text("Voeg foto toe")
        }
    }
}

@Preview
@Composable
fun CreateTicketFormPreview() {
    TemplateApplicationTheme(useDarkTheme = false) {
        CreateTicketForm(routeNumber = "123",
            licensePlate = "1-ABC-123",
            onRouteNumberChange = {},
            onLicensePlateChange = {})
    }
}
