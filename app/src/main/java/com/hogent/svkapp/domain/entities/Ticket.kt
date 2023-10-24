package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.domain.ValidationError
import com.hogent.svkapp.domain.ValidationResult
import com.hogent.svkapp.domain.Validator

class Ticket private constructor(
    val routeNumber: Int, val licensePlate: String, val images: List<Image>
) {
    companion object {
        fun create(
            routeNumber: String, licensePlate: String, images: List<Image>, validator: Validator
        ): CreationResult {
            val routeResult = validator.validateRouteNumber(routeNumber)
            val licenseResult = validator.validateLicensePlate(licensePlate)
            val imageResult = validator.validateImages(images)

            val errors = listOf(routeResult, licenseResult, imageResult)
                .filterIsInstance<ValidationResult.Error>()
                .map { it.error }

            return if (errors.isEmpty()) {
                CreationResult.Success(
                    Ticket(
                        routeNumber = routeNumber.toInt(),
                        licensePlate = licensePlate.trim().uppercase(),
                        images = images
                    )
                )
            } else {
                CreationResult.Failure(errors)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ticket) return false
        return routeNumber == other.routeNumber && licensePlate == other.licensePlate && images == other.images
    }

    override fun hashCode(): Int {
        var result = routeNumber
        result = 31 * result + licensePlate.hashCode()
        result = 31 * result + images.hashCode()
        return result
    }
}

sealed class CreationResult {
    data class Success(val ticket: Ticket) : CreationResult()
    data class Failure(val errors: List<ValidationError>) : CreationResult()
}
