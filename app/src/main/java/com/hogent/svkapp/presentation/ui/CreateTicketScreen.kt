package com.hogent.svkapp.presentation.ui

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
import com.hogent.svkapp.MockCreateTicketModule
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.CreateTicketScreenViewModel

@Composable
fun CreateTicketScreen(createTicketScreenViewModel: CreateTicketScreenViewModel) {
    val routeNumber by createTicketScreenViewModel.routeNumber
    val licensePlate by createTicketScreenViewModel.licensePlate
    val images = remember { createTicketScreenViewModel.images }
    val routeNumberError by createTicketScreenViewModel.routeNumberError
    val licensePlateError by createTicketScreenViewModel.licensePlateError
    val imagesError by createTicketScreenViewModel.imagesError
    val showDialog by createTicketScreenViewModel.showDialog

    Scaffold(floatingActionButton = {
        SendFloatingActionButton(onSend = createTicketScreenViewModel::onSend)
    }) { innerPadding ->
        CreateTicketForm(
            routeNumber = routeNumber,
            licensePlate = licensePlate,
            images = images,
            routeNumberError = routeNumberError,
            licensePlateError = licensePlateError,
            imagesError = imagesError,
            onRouteNumberChange = createTicketScreenViewModel::onRouteNumberChange,
            onLicensePlateChange = createTicketScreenViewModel::onLicensePlateChange,
            onAddImage = createTicketScreenViewModel::addImage,
            onRemoveImage = createTicketScreenViewModel::deleteImage,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(paddingValues = innerPadding)
                .padding(all = MaterialTheme.spacing.large),
        )
        if (showDialog) {
            ConfirmationPopUp(onDismissRequest = createTicketScreenViewModel::toggleDialog)
        }
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketScreenPreview() {
    val mockCreateTicketModule = MockCreateTicketModule()

    TemplateApplicationTheme {
        CreateTicketScreen(
            createTicketScreenViewModel = mockCreateTicketModule.getViewModel()
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketScreenPreviewDark() = CreateTicketScreenPreview()
