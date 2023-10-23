package com.hogent.svkapp.features.create_ticket.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.features.create_ticket.MockCreateTicketModule
import com.hogent.svkapp.features.create_ticket.presentation.viewmodels.CreateTicketScreenViewModel
import com.hogent.svkapp.main.presentation.ui.SVKLogo
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.main.presentation.ui.theme.spacing

@Composable
fun CreateTicketScreen(createTicketScreenViewModel: CreateTicketScreenViewModel) {
    val routeNumber by createTicketScreenViewModel.routeNumber
    val licensePlate by createTicketScreenViewModel.licensePlate
    val images = remember { createTicketScreenViewModel.images }
    val routeNumberError by createTicketScreenViewModel.routeNumberError
    val licensePlateError by createTicketScreenViewModel.licensePlateError
    val imagesError by createTicketScreenViewModel.imagesError

    Scaffold(floatingActionButton = {
        SendFloatingActionButton(onSend = createTicketScreenViewModel::onSend)
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(paddingValues = innerPadding)
                .padding(all = MaterialTheme.spacing.large),
            verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.large)
        ) {
//            SVKLogo()
            CreateTicketForm(
                routeNumber = routeNumber,
                licensePlate = licensePlate,
                images = images,
                onRouteNumberChange = createTicketScreenViewModel::onRouteNumberChange,
                onLicensePlateChange = createTicketScreenViewModel::onLicensePlateChange,
                onAddImage = createTicketScreenViewModel::addImage,
                routeNumberError = routeNumberError,
                licensePlateError = licensePlateError,
                imagesError = imagesError
            )
        }
    }
}

@Composable
fun CreateTicketScreenPreviewBase() {
    val mockCreateTicketModule = MockCreateTicketModule()

    TemplateApplicationTheme {
        CreateTicketScreen(
            createTicketScreenViewModel = mockCreateTicketModule.getViewModel()
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketScreenPreview() {
    CreateTicketScreenPreviewBase()
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketScreenPreviewDark() {
    CreateTicketScreenPreviewBase()
}
