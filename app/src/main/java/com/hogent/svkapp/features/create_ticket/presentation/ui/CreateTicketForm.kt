package com.hogent.svkapp.features.create_ticket.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.main.presentation.ui.theme.spacing

@Composable
fun CreateTicketForm() {
    val text by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        TicketTextField(value = text, label = "Routenummer")
        TicketTextField(value = text, label = "Nummerplaat")
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
            Text("Voeg foto toe")
        }
    }
}

@Preview
@Composable
fun CreateTicketFormPreview() {
    TemplateApplicationTheme(useDarkTheme = false) {
        CreateTicketForm()
    }
}
