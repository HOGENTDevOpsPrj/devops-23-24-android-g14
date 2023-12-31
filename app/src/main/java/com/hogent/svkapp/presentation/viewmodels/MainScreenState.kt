package com.hogent.svkapp.presentation.viewmodels

import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.domain.entities.ImageCollectionError
import com.hogent.svkapp.domain.entities.LicensePlateError
import com.hogent.svkapp.domain.entities.LoadReceiptNumberError
import com.hogent.svkapp.domain.entities.RouteNumberCollectionError
import com.hogent.svkapp.domain.entities.RouteNumberError
import com.hogent.svkapp.domain.entities.User

/**
 * The state of the main screen.
 * @param loadReceiptNumberInputFieldValue The value of the load receipt number input field.
 * @param routeNumberInputFieldValues The values of the route number input fields.
 * @param licensePlateInputFieldValue The value of the license plate input field.
 * @param imageCollection The collection of images.
 * @param loadReceiptNumberInputFieldValidationError The validation error of the load receipt number input field.
 * @param routeNumberInputFieldValidationErrors The validation errors of the route number input fields.
 * @param routeNumberCollectionError The validation error of the route number collection.
 * @param licensePlateInputFieldValidationError The validation error of the license plate input field.
 * @param imageCollectionError The validation error of the image collection.
 * @param showPopup Whether or not to show the confirmation popup.
 * @param user The user that is currently logged in into the app.
 */
data class MainScreenState(
    val loadReceiptNumberInputFieldValue: String = "",
    val routeNumberInputFieldValues: List<String> = listOf(""),
    val licensePlateInputFieldValue: String = "",
    val imageCollection: List<Image> = emptyList(),
    val loadReceiptNumberInputFieldValidationError: LoadReceiptNumberError? = null,
    val routeNumberInputFieldValidationErrors: List<List<RouteNumberError?>> = listOf(emptyList()),
    val routeNumberCollectionError: RouteNumberCollectionError? = null,
    val licensePlateInputFieldValidationError: LicensePlateError? = null,
    val imageCollectionError: ImageCollectionError? = null,
    val showPopup: Boolean = false,
    val user: User? = null,
)
