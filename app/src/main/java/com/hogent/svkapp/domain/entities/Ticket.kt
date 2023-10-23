package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.domain.Validator

data class Ticket private constructor(
    val routeNumber: Int, val licensePlate: String, val images: List<Image>
) {
    companion object {
        fun create(
            validator: Validator, routeNumber: String, licensePlate: String, images: List<Image>
        ): CreationResult {
            val routeError = validator.validateRouteNumber(routeNumber)
            val licenseError = validator.validateLicensePlate(licensePlate)
            val imageError = validator.validateImages(images)

            if (routeError == null && licenseError == null && imageError == null) {
                return CreationResult.Success(
                    Ticket(
                        routeNumber = routeNumber.toInt(),
                        licensePlate = licensePlate.trim().uppercase(),
                        images = images
                    )
                )
            } else {
                val errors = mutableListOf<ErrorType>()
                if (routeError != null) errors.add(routeError)
                if (licenseError != null) errors.add(licenseError)
                if (imageError != null) errors.add(imageError)
                return CreationResult.Failure(errors = errors)
            }
        }
    }
}

sealed class CreationResult {
    data class Success(val ticket: Ticket) : CreationResult()
    data class Failure(val errors: List<ErrorType>) : CreationResult()
}

enum class ErrorType(val message: String) {
    EMPTY_ROUTE("Vul een routenummer in."), INVALID_ROUTE_NUMBER("Dit is geen geldig routenummer."), EMPTY_LICENSE_PLATE(
        "Vul de nummerplaat in."
    ),
    LONG_LICENSE_PLATE("Dit is geen geldige nummerplaat."), EMPTY_IMAGES("Voeg minstens één foto toe.")
}
