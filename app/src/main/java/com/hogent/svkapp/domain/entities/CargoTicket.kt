package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.util.CustomResult
import java.time.LocalDateTime

/**
 * An error that can occur when validating a [CargoTicket].
 */
data class CargoTicketError(
    /**
     * The error that occurred when validating the load receipt number.
     */
    val loadReceiptNumberError: LoadReceiptNumberError?,
    /**
     * The errors that occurred when validating the route numbers.
     */
    val routeNumberErrors: List<List<RouteNumberError?>>,
    /**
     * The error that occurred when validating the route number collection.
     */
    val routeNumberCollectionError: RouteNumberCollectionError?,
    /**
     * The error that occurred when validating the license plate.
     */
    val licensePlateError: LicensePlateError?,
    /**
     * The error that occurred when validating the image collection.
     */
    val imageCollectionError: ImageCollectionError?,
    /**
     * The error that occurred when validating the freight loader id.
     */
    val freightLoaderIdError: Auth0IdError?
) {
    /**
     * Whether or not the [CargoTicketError] has errors.
     */
    val hasErrors: Boolean
        get() {
            return loadReceiptNumberError != null || routeNumberErrors.isEmpty() || routeNumberCollectionError != null || licensePlateError != null || imageCollectionError != null || freightLoaderIdError != null
        }
}

/**
 * A ticket for cargo.
 *
 * @property loadReceiptNumber the load receipt number of the cargo.
 * @property routeNumbers the route numbers of the cargo.
 * @property licensePlate the license plate of the cargo.
 * @property images the images of the cargo.
 * @property freightLoaderId the ID of the freight loader.
 * @property registrationDateTime the date and time of the registration.
 */
class CargoTicket(
    loadReceiptNumber: LoadReceiptNumber,
    routeNumbers: RouteNumberCollection,
    licensePlate: LicensePlate,
    images: ImageCollection,
    freightLoaderId: Auth0Id,
    registrationDateTime: LocalDateTime
) {
    private var _loadReceiptNumber: LoadReceiptNumber = loadReceiptNumber
    val loadReceiptNumber: LoadReceiptNumber get() = _loadReceiptNumber

    private var _routeNumbers: RouteNumberCollection = routeNumbers
    val routeNumbers: RouteNumberCollection get() = _routeNumbers

    private var _licensePlate: LicensePlate = licensePlate
    val licensePlate: LicensePlate get() = _licensePlate

    private var _images: ImageCollection = images
    val images: ImageCollection get() = _images

    private var _freightLoaderId: Auth0Id = freightLoaderId
    val freightLoaderId: Auth0Id get() = _freightLoaderId

    private var _registrationDateTime: LocalDateTime = registrationDateTime
    val registrationDateTime: LocalDateTime get() = _registrationDateTime

    companion object {
        /**
         * Creates a [CargoTicket].
         *
         * If the [loadReceiptNumber], [routeNumbers], [licensePlate], [images] or [freightLoaderId] are invalid, a
         * [CargoTicketError] is returned. A [CargoTicket] is invalid if one of its properties is invalid.
         *
         * @param loadReceiptNumber the load receipt number of the cargo.
         * @param routeNumbers the route numbers of the cargo.
         * @param licensePlate the license plate of the cargo.
         * @param images the images of the cargo.
         * @param freightLoaderId the ID of the freight loader.
         * @param registrationDateTime the date and time of the registration.
         *
         * @return a [CustomResult] containing either a [CargoTicket] or a [CargoTicketError].
         */
        fun create(
            loadReceiptNumber: String,
            routeNumbers: List<String>,
            licensePlate: String,
            images: List<Image>,
            freightLoaderId: String,
            registrationDateTime: LocalDateTime
        ): CustomResult<CargoTicket, CargoTicketError> {
            val result =
                validateStringRepresentations(loadReceiptNumber, routeNumbers, licensePlate, images, freightLoaderId)

            return if (result.hasErrors) {
                CustomResult.Failure(result)
            } else {
                val loadReceiptNumberValidated = LoadReceiptNumber.create(loadReceiptNumber) as CustomResult.Success
                val routeNumberCollection =
                    RouteNumberCollection.create(routeNumbers.map { RouteNumber.create(it) as CustomResult.Success }
                        .map { it.value }) as CustomResult.Success
                val licensePlateValidated = LicensePlate.create(licensePlate) as CustomResult.Success
                val imageCollection = ImageCollection.create(images) as CustomResult.Success
                val freightLoaderIdValidated = Auth0Id.create(freightLoaderId) as CustomResult.Success

                CustomResult.Success(
                    CargoTicket(
                        loadReceiptNumberValidated.value,
                        routeNumberCollection.value,
                        licensePlateValidated.value,
                        imageCollection.value,
                        freightLoaderIdValidated.value,
                        registrationDateTime
                    )
                )
            }
        }

        private fun validateStringRepresentations(
            loadReceiptNumber: String,
            routeNumbers: List<String>,
            licensePlate: String,
            images: List<Image>,
            freightLoaderId: String
        ): CargoTicketError {

            val loadReceiptNumberError = LoadReceiptNumber.validate(loadReceiptNumber)

            val routeNumberResult = RouteNumberCollection.validateStringRepresentations(routeNumbers)

            val routeNumberCollectionError = when (routeNumberResult) {
                is CustomResult.Success -> null
                is CustomResult.Failure -> routeNumberResult.error
            }

            val routeNumberErrors = when (routeNumberResult) {
                is CustomResult.Success -> routeNumberResult.value
                is CustomResult.Failure -> emptyList()
            }

            val licensePlateError = LicensePlate.validate(licensePlate)

            val imageCollectionError = ImageCollection.validate(images)

            val freightLoaderIdError = Auth0Id.validate(freightLoaderId)

            return CargoTicketError(
                loadReceiptNumberError,
                routeNumberErrors,
                routeNumberCollectionError,
                licensePlateError,
                imageCollectionError,
                freightLoaderIdError
            )
        }
    }
}
