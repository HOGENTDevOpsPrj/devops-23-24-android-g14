package com.hogent.svkapp.presentation.ui.mainscreen

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
import com.hogent.svkapp.MockAppModule
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel) {
    val routeNumber by mainScreenViewModel.routeNumber
    val licensePlate by mainScreenViewModel.licensePlate
    val images = remember { mainScreenViewModel.images }
    val routeNumberError by mainScreenViewModel.routeNumberError
    val licensePlateError by mainScreenViewModel.licensePlateError
    val imagesError by mainScreenViewModel.imagesError
    val showDialog by mainScreenViewModel.showDialog

    Scaffold(floatingActionButton = {
        SendFloatingActionButton(onSend = mainScreenViewModel::onSend)
    }) { innerPadding ->
        Form(
            routeNumber = routeNumber,
            licensePlate = licensePlate,
            images = images,
            routeNumberError = routeNumberError,
            licensePlateError = licensePlateError,
            imagesError = imagesError,
            onRouteNumberChange = mainScreenViewModel::onRouteNumberChange,
            onLicensePlateChange = mainScreenViewModel::onLicensePlateChange,
            onAddImage = mainScreenViewModel::addImage,
            onRemoveImage = mainScreenViewModel::deleteImage,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(paddingValues = innerPadding)
                .padding(all = MaterialTheme.spacing.large),
        )
        if (showDialog) {
            ConfirmationDialog(onDismissRequest = mainScreenViewModel::toggleDialog)
        }
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MainScreenPreview() {
    val mockCreateTicketModule = MockAppModule()

    TemplateApplicationTheme {
        MainScreen(
            mainScreenViewModel = mockCreateTicketModule.getMainScreenViewModel()
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreviewDark() = MainScreenPreview()
