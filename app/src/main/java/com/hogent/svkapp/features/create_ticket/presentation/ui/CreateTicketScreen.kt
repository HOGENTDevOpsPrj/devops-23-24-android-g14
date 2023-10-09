package com.hogent.svkapp.features.create_ticket.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.main.presentation.ui.SVKLogo
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.main.presentation.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTicketScreen() {
    Scaffold(floatingActionButton = { SendFloatingActionButton() }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large)
        ) {
            SVKLogo()
            CreateTicketForm()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateTicketScreenPreview() {
    TemplateApplicationTheme(useDarkTheme = false) {
        CreateTicketScreen()
    }
}
