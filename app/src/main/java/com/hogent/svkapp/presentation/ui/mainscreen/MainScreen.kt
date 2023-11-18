package com.hogent.svkapp.presentation.ui.mainscreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.ImageCollectionError
import com.hogent.svkapp.domain.entities.LicensePlateError
import com.hogent.svkapp.domain.entities.RouteNumberCollectionError
import com.hogent.svkapp.domain.entities.RouteNumberError
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

/**
 * The main screen of the app.
 *
 * @param mainScreenViewModel the [MainScreenViewModel] that contains the state of the screen.
 *
 * @sample MainScreenPreview
 * @sample MainScreenPreviewDark
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel = viewModel()) {
    val mainScreenState by mainScreenViewModel.uiState.collectAsState()

    Scaffold(floatingActionButton = {
        SendFloatingActionButton(onSend = mainScreenViewModel::onSend)
    }, topBar = {
        MainTopAppBar(onLogout = mainScreenViewModel::onLogout)
    }) { innerPadding ->
        Form(
            routeNumberInputFieldValues = mainScreenState.routeNumberInputFieldValues,
            licensePlateInputFieldValue = mainScreenState.licensePlateInputFieldValue,
            imageCollection = mainScreenState.imageCollection,
            routeNumberInputFieldValidationErrors = mainScreenState.routeNumberInputFieldValidationErrors.map {
                when (it) {
                    RouteNumberError.Empty -> stringResource(R.string.empty_route_number_error_message)
                    RouteNumberError.InvalidFormat, RouteNumberError.NonPositiveNumber -> stringResource(
                        R.string.invalid_route_number_error_message
                    )

                    null -> null
                }
            },
            routeNumberCollectionError = mainScreenState.routeNumberCollectionError?.let {
                when (it) {
                    RouteNumberCollectionError.Empty -> stringResource(R.string.missing_route_numbers_error_message)
                }
            },
            licensePlateInputFieldValidationError = mainScreenState.licensePlateInputFieldValidationError?.let {
                when (it) {
                    LicensePlateError.Empty -> stringResource(R.string.empty_license_plate_error_message)
                    LicensePlateError.TooLong -> stringResource(R.string.invalid_license_plate_error_message)
                }
            },
            imageCollectionError = mainScreenState.imageCollectionError?.let {
                when (it) {
                    ImageCollectionError.Empty -> stringResource(R.string.missing_images_error_message)
                }
            },
            onRouteNumberChange = mainScreenViewModel::onRouteNumberChange,
            onAddRouteNumber = mainScreenViewModel::addRouteNumber,
            onRemoveRouteNumber = mainScreenViewModel::removeRouteNumber,
            onLicensePlateChange = mainScreenViewModel::onLicensePlateChange,
            onAddImage = mainScreenViewModel::addImage,
            onRemoveImage = mainScreenViewModel::removeImage,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(all = MaterialTheme.spacing.large),
        )
        if (mainScreenState.showDialog) {
            ConfirmationDialog(onDismissRequest = mainScreenViewModel::toggleDialog)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun MainScreenPreview() {
    TemplateApplicationTheme {
        MainScreen(
            mainScreenViewModel = MainScreenViewModel(navController = rememberNavController())
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenPreviewDark() = MainScreenPreview()
