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
import com.hogent.svkapp.features.create_ticket.data.repositories.MockTicketRepository
import com.hogent.svkapp.features.create_ticket.domain.usecases.CreateTicketUseCase
import com.hogent.svkapp.features.create_ticket.presentation.viewmodels.CreateTicketViewModel
import com.hogent.svkapp.main.presentation.ui.SVKLogo
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.main.presentation.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTicketScreen(viewModel: CreateTicketViewModel) {
    Scaffold(floatingActionButton = {
        SendFloatingActionButton(onSend = { viewModel.createTicket() })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(paddingValues = innerPadding)
                .padding(all = MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.large)
        ) {
            SVKLogo()
            CreateTicketForm(routeNumber = viewModel.routeNumber.value,
                licensePlate = viewModel.licensePlate.value,
                onRouteNumberChange = { viewModel.routeNumber.value = it },
                onLicensePlateChange = { viewModel.licensePlate.value = it })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateTicketScreenPreview() {
    val mockCreateTicketUseCase = CreateTicketUseCase(ticketRepository = MockTicketRepository())
    val viewModel = CreateTicketViewModel(mockCreateTicketUseCase)

    TemplateApplicationTheme(useDarkTheme = false) {
        CreateTicketScreen(viewModel = viewModel)
    }
}
