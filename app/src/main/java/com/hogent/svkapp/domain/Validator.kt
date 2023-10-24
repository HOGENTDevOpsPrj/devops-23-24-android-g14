package com.hogent.svkapp.domain

import com.hogent.svkapp.domain.entities.Image

const val MAX_LICENSE_PLATE_LENGTH = 40

class Validator {
    fun validateRouteNumber(routeNumber: String): ValidationResult {
        val cleanRouteNumber = routeNumber.trim().filter { !it.isWhitespace() }

        if (cleanRouteNumber.isEmpty()) return ValidationResult.Error(ValidationError.EMPTY_ROUTE)
        if (cleanRouteNumber.toIntOrNull() == null || cleanRouteNumber.toInt() <= 0) return ValidationResult.Error(ValidationError.INVALID_ROUTE_NUMBER)

        return ValidationResult.Success
    }

    fun validateLicensePlate(licensePlate: String): ValidationResult {
        val cleanLicensePlate = licensePlate.trim().uppercase().filter { !it.isWhitespace() }

        if (cleanLicensePlate.isEmpty()) return ValidationResult.Error(ValidationError.EMPTY_LICENSE_PLATE)
        if (cleanLicensePlate.length > MAX_LICENSE_PLATE_LENGTH) return ValidationResult.Error(ValidationError.LONG_LICENSE_PLATE)

        return ValidationResult.Success
    }

    fun validateImages(images: List<Image>): ValidationResult {
        if (images.isEmpty()) return ValidationResult.Error(ValidationError.EMPTY_IMAGES)

        return ValidationResult.Success
    }
}

sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(val error: ValidationError) : ValidationResult()
}

enum class ValidationError(val message: String) {
    EMPTY_ROUTE("Vul een routenummer in."),
    INVALID_ROUTE_NUMBER("Dit is geen geldig routenummer."),
    EMPTY_LICENSE_PLATE("Vul de nummerplaat in."),
    LONG_LICENSE_PLATE("Dit is geen geldige nummerplaat."),
    EMPTY_IMAGES("Voeg minstens één foto toe.")
}
