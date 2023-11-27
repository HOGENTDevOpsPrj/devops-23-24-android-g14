package com.hogent.svkapp.presentation.ui.mainscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.ImageCollectionError
import com.hogent.svkapp.domain.entities.LicensePlateError
import com.hogent.svkapp.domain.entities.RouteNumberCollectionError
import com.hogent.svkapp.domain.entities.RouteNumberError

/**
 * Converts the given [errorList] to the corresponding List<String?>.
 */
@Composable
fun routeNumberErrors(errorList: List<RouteNumberError?>): List<String?> {
    return errorList.map {
        when (it) {
            RouteNumberError.Empty -> stringResource(id = R.string.empty_route_number_error_message)
            RouteNumberError.InvalidFormat -> stringResource(R.string.invalid_route_number_error_message)
            RouteNumberError.NonPositiveNumber -> stringResource(R.string.invalid_route_number_error_message)
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
            RouteNumberCollectionError.Empty -> listOf(stringResource(R.string.missing_route_numbers_error_message))
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
            LicensePlateError.Empty -> listOf(stringResource(R.string.empty_license_plate_error_message))
            LicensePlateError.TooLong -> listOf(stringResource(R.string.invalid_license_plate_error_message))
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
            ImageCollectionError.Empty -> listOf(stringResource(R.string.missing_images_error_message))
            null -> null
        }
    }
}
