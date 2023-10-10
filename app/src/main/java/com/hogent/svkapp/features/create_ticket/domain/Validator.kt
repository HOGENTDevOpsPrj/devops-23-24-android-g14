package com.hogent.svkapp.features.create_ticket.domain

import com.hogent.svkapp.features.create_ticket.domain.entities.ValidationResult

private const val MAX_LICENSE_PLATE_LENGTH = 40

class Validator {
    fun validateLicensePlate(licensePlate: String): ValidationResult {
        val cleanLicensePlate = licensePlate.trim().uppercase()

        return if (cleanLicensePlate == "") {
            ValidationResult.Invalid(message = "Vul een nummerplaat in.")
        } else if (cleanLicensePlate.length > MAX_LICENSE_PLATE_LENGTH) {
            ValidationResult.Invalid(message = "Nummerplaat is te lang.")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateRouteNumber(routeNumber: String): ValidationResult {
        val cleanRouteNumber = routeNumber.trim()

        return if (cleanRouteNumber == "") {
            ValidationResult.Invalid(message = "Vul een routenummer in.")
        } else if (cleanRouteNumber.toIntOrNull() == null || cleanRouteNumber.toInt() <= 0) {
            ValidationResult.Invalid(message = "Routenummer is ongeldig.")
        } else {
            ValidationResult.Valid
        }
    }

    fun sanitizeLicensePlate(licensePlate: String): String {
        return licensePlate.trim().uppercase()
    }

    fun sanitizeRouteNumber(routeNumber: String): Int {
        return routeNumber.trim().toInt()
    }
}
