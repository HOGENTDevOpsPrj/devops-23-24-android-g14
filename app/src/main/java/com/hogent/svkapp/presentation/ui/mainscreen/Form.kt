package com.hogent.svkapp.presentation.ui.mainscreen

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.presentation.ui.TextField
import com.hogent.svkapp.presentation.ui.mainscreen.images.AddImageButton
import com.hogent.svkapp.presentation.ui.mainscreen.images.ScrollableImageList
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing

/**
 * A form for the user to fill in the details of a cargo ticket.
 *
 * @param routeNumberInputFieldValues The values of the route number input fields.
 * @param licensePlateInputFieldValue The value of the license plate input field.
 * @param imageCollection The collection of images.
 * @param routeNumberInputFieldValidationErrors The validation errors of the route number input fields.
 * @param routeNumberCollectionError The validation error of the route number collection.
 * @param licensePlateInputFieldValidationError The validation error of the license plate input field.
 * @param imageCollectionError The validation error of the image collection.
 * @param onRouteNumberChange The callback to be invoked when the value of a route number input field changes.
 * @param onAddRouteNumber The callback to be invoked when the user wants to add a route number input field.
 * @param onRemoveRouteNumber The callback to be invoked when the user wants to remove a route number input field.
 * @param onLicensePlateChange The callback to be invoked when the value of the license plate input field changes.
 * @param onAddImage The callback to be invoked when the user wants to add an image.
 * @param onRemoveImage The callback to be invoked when the user wants to remove an image.
 * @param modifier The modifier to be applied to the form.
 *
 * @sample FormPreview
 * @sample FormPreviewDark
 * @sample FormPreviewWithRouteNumbers
 * @sample FormPreviewWithRouteNumbersDark
 * @sample FormPreviewWithLicensePlate
 * @sample FormPreviewWithLicensePlateDark
 * @sample FormPreviewWithImages
 * @sample FormPreviewWithImagesDark
 * @sample FormPreviewWithRouteNumberErrors
 * @sample FormPreviewWithRouteNumberErrorsDark
 * @sample FormPreviewWithLicensePlateError
 * @sample FormPreviewWithLicensePlateErrorDark
 * @sample FormPreviewWithImageError
 * @sample FormPreviewWithImageErrorDark
 * @sample FormPreviewWithRouteNumberCollectionError
 * @sample FormPreviewWithRouteNumberCollectionErrorDark
 * @sample FormPreviewWithAllErrors
 * @sample FormPreviewWithAllErrorsDark
 * @sample FormPreviewWithAll
 * @sample FormPreviewWithAllDark
 */
@Composable
fun Form(
    routeNumberInputFieldValues: List<String>,
    licensePlateInputFieldValue: String,
    imageCollection: List<Image>,
    routeNumberInputFieldValidationErrors: List<String?>,
    routeNumberCollectionError: String?,
    licensePlateInputFieldValidationError: String?,
    imageCollectionError: String?,
    onRouteNumberChange: (Int, String) -> Unit,
    onAddRouteNumber: () -> Unit,
    onRemoveRouteNumber: (Int) -> Unit,
    onLicensePlateChange: (String) -> Unit,
    onAddImage: (Image) -> Unit,
    onRemoveImage: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    val takePictureLauncher = getTakePictureLauncher(onAddImage)

    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = modifier
    ) {
        routeNumberInputFieldValues.forEachIndexed { index, routeNumber ->
            CustomTextField(value = routeNumber,
                label = stringResource(R.string.route_number_text_field_label, index + 1),
                onValueChange = { newRouteNumber -> onRouteNumberChange(index, newRouteNumber) },
                error = routeNumberInputFieldValidationErrors.getOrNull(index),
                keyboardType = KeyboardType.Number,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { onRemoveRouteNumber(index) }) {
                        Icon(
                            Icons.Filled.Close, contentDescription = stringResource(
                                R.string.remove_route_number_button_content_description, index + 1
                            )
                        )
                    }
                })
        }
        if (routeNumberCollectionError != null) {
            Text(text = routeNumberCollectionError, color = MaterialTheme.colorScheme.error)
        }

        Button(onClick = onAddRouteNumber) {
            Text(text = stringResource(R.string.add_extra_route_number_button_text))
        }

        CustomTextField(
            value = licensePlateInputFieldValue,
            label = stringResource(R.string.license_plate_label_text),
            onValueChange = onLicensePlateChange,
            error = licensePlateInputFieldValidationError,
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth(),
        )
        if (imageCollectionError != null) {
            Text(text = imageCollectionError, color = MaterialTheme.colorScheme.error)
        } else {
            ScrollableImageList(imageList = imageCollection, onDeleteImage = onRemoveImage)
        }
        AddImageButton(
            onClick = { takePictureLauncher.launch(null) }, modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun getTakePictureLauncher(onAddImage: (Image) -> Unit) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.TakePicturePreview()
) { bitmap ->
    bitmap?.let {
        onAddImage(Image.BitmapImage(bitmap))
    }
}

@Composable
private fun PreviewWrapper(
    routeNumberInputFieldValues: List<String> = listOf(),
    licensePlateInputFieldValue: String = "",
    imageCollection: List<Image> = listOf(),
    routeNumberInputFieldValidationErrors: List<String?> = listOf(),
    routeNumberCollectionError: String? = null,
    licensePlateInputFieldValidationError: String? = null,
    imageCollectionError: String? = null
) {
    TemplateApplicationTheme {
        Form(routeNumberInputFieldValues = routeNumberInputFieldValues,
            licensePlateInputFieldValue = licensePlateInputFieldValue,
            imageCollection = imageCollection,
            routeNumberInputFieldValidationErrors = routeNumberInputFieldValidationErrors,
            routeNumberCollectionError = routeNumberCollectionError,
            licensePlateInputFieldValidationError = licensePlateInputFieldValidationError,
            imageCollectionError = imageCollectionError,
            onRouteNumberChange = { _: Int, _: String -> },
            onAddRouteNumber = {},
            onRemoveRouteNumber = {},
            onLicensePlateChange = {},
            onAddImage = {},
            onRemoveImage = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FormPreview() = PreviewWrapper()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FormPreviewDark() = FormPreview()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FormPreviewWithRouteNumbers() =
    PreviewWrapper(routeNumberInputFieldValues = listOf("1", "2", "3"))

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FormPreviewWithRouteNumbersDark() = FormPreviewWithRouteNumbers()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FormPreviewWithLicensePlate() =
    PreviewWrapper(licensePlateInputFieldValue = stringResource(R.string.previews_license_plate))

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FormPreviewWithLicensePlateDark() = FormPreviewWithLicensePlate()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FormPreviewWithImages() = PreviewWrapper(
    imageCollection = listOf(
        Image.BitmapImage(
            android.graphics.Bitmap.createBitmap(
                100, 100, android.graphics.Bitmap.Config.ARGB_8888
            )
        )
    )
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FormPreviewWithImagesDark() = FormPreviewWithImages()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FormPreviewWithRouteNumberErrors() = PreviewWrapper(
    routeNumberInputFieldValues = listOf("", "2", ""),
    routeNumberInputFieldValidationErrors = listOf(
        stringResource(R.string.empty_route_number_error_message), null, stringResource(
            R.string.invalid_route_number_error_message
        )
    )
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FormPreviewWithRouteNumberErrorsDark() = FormPreviewWithRouteNumberErrors()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FormPreviewWithLicensePlateError() =
    PreviewWrapper(licensePlateInputFieldValidationError = stringResource(R.string.invalid_license_plate_error_message))

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FormPreviewWithLicensePlateErrorDark() = FormPreviewWithLicensePlateError()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FormPreviewWithImageError() =
    PreviewWrapper(imageCollectionError = stringResource(R.string.missing_images_error_message))

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FormPreviewWithImageErrorDark() = FormPreviewWithImageError()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FormPreviewWithRouteNumberCollectionError() =
    PreviewWrapper(routeNumberCollectionError = stringResource(R.string.missing_route_numbers_error_message))

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FormPreviewWithRouteNumberCollectionErrorDark() =
    FormPreviewWithRouteNumberCollectionError()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FormPreviewWithAllErrors() = PreviewWrapper(
    routeNumberInputFieldValues = listOf("", "2", ""),
    routeNumberInputFieldValidationErrors = listOf(
        stringResource(R.string.empty_route_number_error_message), null, stringResource(
            R.string.invalid_route_number_error_message
        )
    ),
    licensePlateInputFieldValidationError = stringResource(R.string.empty_route_number_error_message),
    routeNumberCollectionError = stringResource(R.string.missing_route_numbers_error_message),
    imageCollectionError = stringResource(R.string.missing_images_error_message),
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FormPreviewWithAllErrorsDark() = FormPreviewWithAllErrors()

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FormPreviewWithAll() = PreviewWrapper(
    routeNumberInputFieldValues = listOf("1", "2", "3"),
    licensePlateInputFieldValue = stringResource(R.string.previews_license_plate),
    imageCollection = listOf(
        Image.BitmapImage(
            android.graphics.Bitmap.createBitmap(
                100, 100, android.graphics.Bitmap.Config.ARGB_8888
            )
        )
    ),
    routeNumberInputFieldValidationErrors = listOf(
        stringResource(R.string.empty_route_number_error_message), null, stringResource(
            R.string.invalid_route_number_error_message
        )
    ),
    licensePlateInputFieldValidationError = stringResource(R.string.empty_route_number_error_message),
    routeNumberCollectionError = stringResource(R.string.missing_route_numbers_error_message),
    imageCollectionError = stringResource(R.string.missing_images_error_message),
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FormPreviewWithAllDark() = FormPreviewWithAll()
