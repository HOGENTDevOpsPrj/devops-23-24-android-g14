package com.hogent.svkapp.features.create_ticket.domain

import com.hogent.svkapp.features.create_ticket.domain.entities.ValidationResult

private const val MAX_LICENSE_PLATE_LENGTH = 40

class Validator {
    fun validateLicensePlate(licensePlate: String): ValidationResult {
        return if (licensePlate.isNotEmpty()) {
            if (licensePlate.length <= MAX_LICENSE_PLATE_LENGTH) {
                ValidationResult.Valid
            } else {
                ValidationResult.Invalid(message = "Nummerplaat is ongeldig.")
            }
        } else {
            ValidationResult.Invalid(message = "Vul een nummerplaat in.")
        }
    }

    fun validateRouteNumber(routeNumber: String): ValidationResult {
        return if (routeNumber.toIntOrNull() != null && routeNumber.toInt() > 0) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(message = "Routenummer is ongeldig.")
        }
    }
}