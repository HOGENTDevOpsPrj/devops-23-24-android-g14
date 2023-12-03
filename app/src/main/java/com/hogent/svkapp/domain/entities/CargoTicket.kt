package com.hogent.svkapp.domain.entities

import android.util.Log
import kotlinx.serialization.Serializable

/**
 * An error that can occur when validating a [CargoTicket].
 */
data class CargoTicketError(
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
    val imageCollectionError: ImageCollectionError?
) {
    /**
     * Whether or not the [CargoTicketError] has errors.
     */
    val hasErrors: Boolean
        get() {
            return routeNumberCollectionError != null || licensePlateError != null || imageCollectionError != null ||
                    routeNumberErrors.isEmpty()
        }
}

/**
 * A ticket for cargo.
 *
 * @property routeNumbers the route numbers of the cargo.
 * @property licensePlate the license plate of the cargo.
 * @property images the images of the cargo.
 */
class CargoTicket public constructor(
    routeNumbers: RouteNumberCollection, licensePlate: LicensePlate, images: ImageCollection
) {
    private var _routeNumbers: RouteNumberCollection = routeNumbers
    val routeNumbers: RouteNumberCollection get() = _routeNumbers

    private var _licensePlate: LicensePlate = licensePlate
    val licensePlate: LicensePlate get() = _licensePlate

    private var _images: ImageCollection = images
    val images: ImageCollection get() = _images

    companion object {
        /**
         * Creates a [CargoTicket].
         *
         * If the [routeNumbers], [licensePlate] or [images] are invalid, a [CargoTicketError] is returned.
         *
         * @param routeNumbers the route numbers of the cargo.
         * @param licensePlate the license plate of the cargo.
         * @param images the images of the cargo.
         *
         * @return a [Result] containing either a [CargoTicket] or a [CargoTicketError].
         */
        fun create(
            routeNumbers: List<String>, licensePlate: String, images: List<Image>
        ): Result<CargoTicket, CargoTicketError> {
            val result = validateStringRepresentations(routeNumbers, licensePlate, images)

            return if (result.hasErrors) {
                Result.Failure(result)
            } else {
                val routeNumberCollection =
                    RouteNumberCollection.create(routeNumbers.map { RouteNumber.create(it) as Result.Success }
                        .map { it.value }) as Result.Success
                val licensePlateValidated = LicensePlate.create(licensePlate) as Result.Success
                val imageCollection = ImageCollection.create(images) as Result.Success

                Result.Success(
                    CargoTicket(
                        routeNumberCollection.value, licensePlateValidated.value, imageCollection.value
                    )
                )
            }
        }

        private fun validateStringRepresentations(
            routeNumbers: List<String>, licensePlate: String, images: List<Image>
        ): CargoTicketError {
            val routeNumberResult = RouteNumberCollection.validateStringRepresentations(routeNumbers)

            val routeNumberCollectionError = when (routeNumberResult) {
                is Result.Success -> null
                is Result.Failure -> routeNumberResult.error
            }

            val routeNumberErrors = when (routeNumberResult) {
                is Result.Success -> routeNumberResult.value
                is Result.Failure -> emptyList()
            }

            val licensePlateError = LicensePlate.validate(licensePlate)

            val imageCollectionError = ImageCollection.validate(images)

            return CargoTicketError(
                routeNumberErrors, routeNumberCollectionError, licensePlateError, imageCollectionError
            )
        }
    }
}
