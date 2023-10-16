package com.hogent.svkapp.features.create_ticket.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.features.create_ticket.MockCreateTicketModule
import com.hogent.svkapp.features.create_ticket.presentation.viewmodels.CreateTicketViewModel
import com.hogent.svkapp.features.upload_photo.MockUploadPhotoModule
import com.hogent.svkapp.features.upload_photo.presentation.viewmodel.UploadPhotoViewModel
import com.hogent.svkapp.main.presentation.ui.SVKLogo
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.main.presentation.ui.theme.spacing

@Composable
fun CreateTicketScreen(
    createTicketViewModel: CreateTicketViewModel, uploadPhotoViewModel: UploadPhotoViewModel
) {
    Scaffold(floatingActionButton = {
        SendFloatingActionButton(onSend = createTicketViewModel::onSend)
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
            CreateTicketForm(
                routeNumber = createTicketViewModel.routeNumber.value,
                licensePlate = createTicketViewModel.licensePlate.value,
                onRouteNumberChange = createTicketViewModel::onRouteNumberChange,
                onLicensePlateChange = createTicketViewModel::onLicensePlateChange,
                routeNumberError = createTicketViewModel.routeNumberError.value,
                licensePlateError = createTicketViewModel.licensePlateError.value,
                uploadPhotoViewModel = uploadPhotoViewModel
            )
        }
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateTicketScreenPreview() {
    val mockCreateTicketModule = MockCreateTicketModule()

    TemplateApplicationTheme {
        CreateTicketScreen(
            createTicketViewModel = mockCreateTicketModule.getViewModel(),
            uploadPhotoViewModel = MockUploadPhotoModule().getViewModel()
        )
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateTicketScreenPreviewDark() {
    val mockCreateTicketModule = MockCreateTicketModule()

    TemplateApplicationTheme {
        CreateTicketScreen(
            createTicketViewModel = mockCreateTicketModule.getViewModel(),
            uploadPhotoViewModel = MockUploadPhotoModule().getViewModel()
        )
    }
}
