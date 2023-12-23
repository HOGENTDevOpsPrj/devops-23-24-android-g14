package com.hogent.svkapp.presentation.ui.mainscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.ImageCollectionError
import com.hogent.svkapp.domain.entities.LicensePlateError
import com.hogent.svkapp.domain.entities.LoadReceiptNumberError
import com.hogent.svkapp.domain.entities.RouteNumberCollectionError
import com.hogent.svkapp.domain.entities.RouteNumberError

/**
 * Converts the given [error] to the corresponding List<String?>.
 */
@Composable
fun loadReceiptNumberInputFieldValidationError(error: LoadReceiptNumberError?): List<String?>? {
    return error.let {
        when (it) {
            LoadReceiptNumberError.EMPTY -> listOf(stringResource(R.string.empty_load_receipt_number_error_message))
            LoadReceiptNumberError.INVALID -> listOf(stringResource(R.string.invalid_load_receipt_number_error_message))
            null -> null
        }
    }
}

/**
 * Converts the given [errorList] to the corresponding List<String?>.
 */
@Composable
fun routeNumberErrors(errorList: List<RouteNumberError?>): List<String?> {
    return errorList.map {
        when (it) {
            RouteNumberError.EMPTY -> stringResource(id = R.string.empty_route_number_error_message)
            RouteNumberError.INVALID_FORMAT -> stringResource(R.string.invalid_route_number_error_message)
            RouteNumberError.NON_POSITIVE_NUMBER -> stringResource(R.string.invalid_route_number_error_message)
            null -> null
        }
    }
}

/**
 * Converts the given [error] to the corresponding List<String?>.
 */
@Composable
fun routeNumberCollectionError(error: RouteNumberCollectionError?): List<String?>? {
    return error.let {
        when (it) {
            RouteNumberCollectionError.EMPTY -> listOf(stringResource(R.string.missing_route_numbers_error_message))
            null -> null
        }
    }
}

/**
 * Converts the given [error] to the corresponding List<String?>.
 */
@Composable
fun licencePlateInputFieldValidationError(error: LicensePlateError?): List<String?>? {
    return error.let {
        when (it) {
            LicensePlateError.EMPTY -> listOf(stringResource(R.string.empty_license_plate_error_message))
            LicensePlateError.TOO_LONG -> listOf(stringResource(R.string.invalid_license_plate_error_message))
            null -> null
        }
    }
}

/**
 * Converts the given [error] to the corresponding List<String?>.
 */
@Composable
fun imageCollectionError(error: ImageCollectionError?): List<String?>? {
    return error.let {
        when (it) {
            ImageCollectionError.EMPTY -> listOf(stringResource(R.string.missing_images_error_message))
            null -> null
        }
    }
}
