package com.hogent.svkapp.presentation.ui.mainscreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
// * @sample FormPreview
// * @sample FormPreviewDark
// * @sample FormPreviewWithRouteNumbers
// * @sample FormPreviewWithRouteNumbersDark
// * @sample FormPreviewWithLicensePlate
// * @sample FormPreviewWithLicensePlateDark
// * @sample FormPreviewWithImages
// * @sample FormPreviewWithImagesDark
// * @sample FormPreviewWithRouteNumberErrors
// * @sample FormPreviewWithRouteNumberErrorsDark
// * @sample FormPreviewWithLicensePlateError
// * @sample FormPreviewWithLicensePlateErrorDark
// * @sample FormPreviewWithImageError
// * @sample FormPreviewWithImageErrorDark
// * @sample FormPreviewWithRouteNumberCollectionError
// * @sample FormPreviewWithRouteNumberCollectionErrorDark
// * @sample FormPreviewWithAllErrors
// * @sample FormPreviewWithAllErrorsDark
// * @sample FormPreviewWithAll
// * @sample FormPreviewWithAllDark
// */
@Composable
fun Form(
    modifier: Modifier = Modifier,
    mainScreenViewModel: MainScreenViewModel
) {
    val mainScreenState by mainScreenViewModel.uiState.collectAsState()

    val takePictureLauncher = getTakePictureLauncher { mainScreenViewModel.addImage(it) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
    ) {
        item {
            mainScreenState.routeNumberInputFieldValues.forEachIndexed { index, routeNumber ->
                CustomTextField(
                    index = index,
                    value = routeNumber,
                    label = stringResource(R.string.route_number_text_field_label, index + 1),
                    onValueChange = { newRouteNumber ->
                        mainScreenViewModel.onRouteNumberChange(
                            index,
                            newRouteNumber
                        )
                    },
                    errors = routeNumberErrors(mainScreenState.routeNumberInputFieldValidationErrors[index]),
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.fillMaxWidth(),
                    onDelete = { mainScreenViewModel.removeRouteNumber(index) },
                    removable = index != 0,
                ) {
                }
            }
        }
        item {
            if (mainScreenState.routeNumberCollectionError != null) {
                routeNumberCollectionError(mainScreenState.routeNumberCollectionError)?.getOrNull(0)?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
        item {
            Button(onClick = { mainScreenViewModel.addRouteNumber() }) {
                Text(text = stringResource(R.string.add_extra_route_number_button_text))
            }
        }
        item {
            CustomTextField(
                index = 0,
                value = mainScreenState.licensePlateInputFieldValue,
                label = stringResource(R.string.license_plate_label_text),
                onValueChange = { newLicencePlate ->
                    mainScreenViewModel.onLicensePlateChange(
                        newLicencePlate
                    )
                },
                errors = licencePlateInputFieldValidationError(mainScreenState.licensePlateInputFieldValidationError),
                keyboardType = KeyboardType.Text,
                modifier = Modifier.fillMaxWidth(),
                removable = false,
                onDelete = {},
            )
        }
        item {
            if (mainScreenState.imageCollectionError != null) {
                imageCollectionError(mainScreenState.imageCollectionError)?.getOrNull(0)?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } else {
                ScrollableImageList(
                    mainScreenViewModel = mainScreenViewModel,
                )
            }
        }
        item {
            AddImageButton(
                onClick = { takePictureLauncher.launch(null) }, modifier = Modifier.fillMaxWidth()
            )
        }
    }
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
