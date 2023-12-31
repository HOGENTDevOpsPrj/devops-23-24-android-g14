package com.hogent.svkapp.presentation.ui.mainscreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.mainscreen.images.AddImageButton
import com.hogent.svkapp.presentation.ui.mainscreen.images.ScrollableImageList
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

/**
 * A form for the user to fill in the details of a cargo ticket.
 *
 * @param modifier The modifier to be applied to the form.
 *
 */

@Composable
fun Form(
    modifier: Modifier = Modifier,
    mainScreenViewModel: MainScreenViewModel,
    navigateToQrScanner: (Int) -> Unit,
) {
    val mainScreenState by mainScreenViewModel.uiState.collectAsState()

    var currentCameraAction by remember { mutableStateOf<CameraAction?>(null) }

    var showPermissionDialog by remember { mutableStateOf(false) }
    val takePictureLauncher = getTakePictureLauncher { mainScreenViewModel.addImage(it) }

    val permission = android.Manifest.permission.CAMERA
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            when (currentCameraAction) {
                CameraAction.TAKE_PICTURE -> takePictureLauncher.launch(null)
                CameraAction.SCAN_QR -> {}
                null -> {}
            }
        } else {
            showPermissionDialog = true
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium), modifier = modifier
    ) {
        item {
            CustomTextField(modifier = Modifier.fillMaxWidth(),
                index = 0,
                value = mainScreenState.loadReceiptNumberInputFieldValue,
                label = stringResource(R.string.load_receipt_number_label_text),
                onValueChange = { newLoadReceiptNumber ->
                    mainScreenViewModel.onLoadReceiptNumberChange(
                        newLoadReceiptNumber
                    )
                },
                onDelete = {},
                errors = loadReceiptNumberInputFieldValidationError(mainScreenState.loadReceiptNumberInputFieldValidationError),
                navigateToQrScanner = {},
                keyboardType = KeyboardType.Text,
                removable = false,
                scannable = false,
                onRequestPermission = {
                    currentCameraAction = CameraAction.SCAN_QR
                    permissionLauncher.launch(permission)
                })
        }
        item {
            mainScreenState.routeNumberInputFieldValues.mapIndexed { index, routeNumber ->
                CustomTextField(index = index,
                    value = routeNumber,
                    label = stringResource(R.string.route_number_text_field_label, index + 1),
                    onValueChange = { newRouteNumber ->
                        mainScreenViewModel.onRouteNumberChange(
                            index, newRouteNumber
                        )
                    },
                    errors = routeNumberErrors(mainScreenState.routeNumberInputFieldValidationErrors[index]),
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.fillMaxWidth(),
                    onDelete = { mainScreenViewModel.removeRouteNumber(index) },
                    removable = index != 0,
                    scannable = true,
                    navigateToQrScanner = navigateToQrScanner,
                    onRequestPermission = {
                        currentCameraAction = CameraAction.SCAN_QR
                        permissionLauncher.launch(permission)
                    })
            }
            if (mainScreenState.routeNumberCollectionError != null) {
                routeNumberCollectionError(mainScreenState.routeNumberCollectionError)?.getOrNull(0)?.let {
                    Text(
                        text = it, color = MaterialTheme.colorScheme.error
                    )
                }
            }
            Button(onClick = { mainScreenViewModel.addRouteNumber() }) {
                Text(text = stringResource(R.string.add_extra_route_number_button_text))
            }
        }
        item {
            CustomTextField(modifier = Modifier.fillMaxWidth(),
                index = 0,
                value = mainScreenState.licensePlateInputFieldValue,
                label = stringResource(R.string.license_plate_label_text),
                onValueChange = { newLicencePlate ->
                    mainScreenViewModel.onLicensePlateChange(
                        newLicencePlate
                    )
                },
                onDelete = {},
                errors = licencePlateInputFieldValidationError(mainScreenState.licensePlateInputFieldValidationError),
                navigateToQrScanner = {},
                keyboardType = KeyboardType.Text,
                removable = false,
                scannable = false,
                onRequestPermission = {
                    currentCameraAction = CameraAction.SCAN_QR
                    permissionLauncher.launch(permission)
                })
        }
        item {
            if (mainScreenState.imageCollectionError != null) {
                imageCollectionError(mainScreenState.imageCollectionError)?.getOrNull(0)?.let {
                    Text(
                        text = it, color = MaterialTheme.colorScheme.error
                    )
                }
            } else {
                ScrollableImageList(
                    mainScreenViewModel = mainScreenViewModel,
                )
            }
            AddImageButton(
                onClick = {
                    currentCameraAction = CameraAction.TAKE_PICTURE
                    permissionLauncher.launch(permission)
                }, modifier = Modifier.fillMaxWidth()
            )
        }
    }
    if (showPermissionDialog) {
        AlertDialog(onDismissRequest = { showPermissionDialog = false },
            title = { Text(stringResource(R.string.CameraPermissionRequestDialogTitle)) },
            text = { Text(stringResource(R.string.CameraPermissionRequestDialogText)) },
            confirmButton = {
                Button(onClick = {
                    showPermissionDialog = false
                    permissionLauncher.launch(permission)
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                Button(onClick = {
                    showPermissionDialog = false
                }) {
                    Text(stringResource(R.string.Cancel))
                }
            })
    }
}

/**
 * An enum that represents the possible actions that can be performed with the camera.
 */
enum class CameraAction {
    /**
     * Take a picture.
     */
    TAKE_PICTURE,

    /**
     * Scan a QR code.
     */
    SCAN_QR
}

@Composable
private fun getTakePictureLauncher(onAddImage: (Image) -> Unit) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.TakePicturePreview()
) { bitmap ->
    bitmap?.let {
        onAddImage(Image(bitmap = bitmap))
    }
}

//@Composable
//private fun PreviewWrapper(
//    routeNumberInputFieldValues: List<String> = listOf(),
//    licensePlateInputFieldValue: String = "",
//    imageCollection: List<Image> = listOf(),
//    routeNumberInputFieldValidationErrors: List<String?> = listOf(),
//    routeNumberCollectionError: String? = null,
//    licensePlateInputFieldValidationError: String? = null,
//    imageCollectionError: String? = null
//) {
//    TemplateApplicationTheme {
//        Form(
//        )
//    }
//}
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun FormPreview() = PreviewWrapper()
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun FormPreviewDark() = FormPreview()
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun FormPreviewWithRouteNumbers() = PreviewWrapper(routeNumberInputFieldValues = listOf("1", "2", "3"))
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun FormPreviewWithRouteNumbersDark() = FormPreviewWithRouteNumbers()
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun FormPreviewWithLicensePlate() =
//    PreviewWrapper(licensePlateInputFieldValue = stringResource(R.string.previews_license_plate))
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun FormPreviewWithLicensePlateDark() = FormPreviewWithLicensePlate()
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun FormPreviewWithImages() = PreviewWrapper(
//    imageCollection = listOf(
//        Image(
//            bitmap = android.graphics.Bitmap.createBitmap(100, 100, android.graphics.Bitmap.Config.ARGB_8888)
//        )
//    )
//)
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun FormPreviewWithImagesDark() = FormPreviewWithImages()
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun FormPreviewWithRouteNumberErrors() = PreviewWrapper(
//    routeNumberInputFieldValues = listOf("", "2", ""), routeNumberInputFieldValidationErrors = listOf(
//        stringResource(R.string.empty_route_number_error_message), null, stringResource(
//            R.string.invalid_route_number_error_message
//        )
//    )
//)
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun FormPreviewWithRouteNumberErrorsDark() = FormPreviewWithRouteNumberErrors()
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun FormPreviewWithLicensePlateError() =
//    PreviewWrapper(licensePlateInputFieldValidationError = stringResource(R.string.invalid_license_plate_error_message))
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun FormPreviewWithLicensePlateErrorDark() = FormPreviewWithLicensePlateError()
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun FormPreviewWithImageError() =
//    PreviewWrapper(imageCollectionError = stringResource(R.string.missing_images_error_message))
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun FormPreviewWithImageErrorDark() = FormPreviewWithImageError()
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun FormPreviewWithRouteNumberCollectionError() =
//    PreviewWrapper(routeNumberCollectionError = stringResource(R.string.missing_route_numbers_error_message))
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun FormPreviewWithRouteNumberCollectionErrorDark() = FormPreviewWithRouteNumberCollectionError()
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun FormPreviewWithAllErrors() = PreviewWrapper(
//    routeNumberInputFieldValues = listOf("", "2", ""),
//    routeNumberInputFieldValidationErrors = listOf(
//        stringResource(R.string.empty_route_number_error_message), null, stringResource(
//            R.string.invalid_route_number_error_message
//        )
//    ),
//    licensePlateInputFieldValidationError = stringResource(R.string.empty_route_number_error_message),
//    routeNumberCollectionError = stringResource(R.string.missing_route_numbers_error_message),
//    imageCollectionError = stringResource(R.string.missing_images_error_message),
//)
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun FormPreviewWithAllErrorsDark() = FormPreviewWithAllErrors()
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun FormPreviewWithAll() = PreviewWrapper(
//    routeNumberInputFieldValues = listOf("1", "2", "3"),
//    licensePlateInputFieldValue = stringResource(R.string.previews_license_plate),
//    imageCollection = listOf(
//        Image(
//            bitmap = android.graphics.Bitmap.createBitmap(100, 100, android.graphics.Bitmap.Config.ARGB_8888)
//        )
//    ),
//    routeNumberInputFieldValidationErrors = listOf(
//        stringResource(R.string.empty_route_number_error_message), null, stringResource(
//            R.string.invalid_route_number_error_message
//        )
//    ),
//    licensePlateInputFieldValidationError = stringResource(R.string.empty_route_number_error_message),
//    routeNumberCollectionError = stringResource(R.string.missing_route_numbers_error_message),
//    imageCollectionError = stringResource(R.string.missing_images_error_message),
//)
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun FormPreviewWithAllDark() = FormPreviewWithAll()
