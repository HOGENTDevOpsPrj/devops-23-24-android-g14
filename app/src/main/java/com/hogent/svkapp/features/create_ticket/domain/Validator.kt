package com.hogent.svkapp.features.create_ticket.domain

import com.hogent.svkapp.features.create_ticket.domain.entities.ErrorType
import com.hogent.svkapp.features.create_ticket.domain.entities.Image

const val MAX_LICENSE_PLATE_LENGTH = 40

class Validator {
    fun validateRouteNumber(routeNumber: String): ErrorType? {
        val cleanRouteNumber = routeNumber.trim().filter { !it.isWhitespace() }

        if (cleanRouteNumber.isEmpty()) return ErrorType.EMPTY_ROUTE
        if (cleanRouteNumber.toIntOrNull() == null) return ErrorType.INVALID_ROUTE_NUMBER
        if (cleanRouteNumber.toInt() <= 0) return ErrorType.INVALID_ROUTE_NUMBER
        return null
    }

    fun validateLicensePlate(licensePlate: String): ErrorType? {
        val cleanLicensePlate = licensePlate.trim().uppercase().filter { !it.isWhitespace() }

        if (cleanLicensePlate.isEmpty()) return ErrorType.EMPTY_LICENSE_PLATE
        if (cleanLicensePlate.length > MAX_LICENSE_PLATE_LENGTH) return ErrorType.LONG_LICENSE_PLATE
        return null
    }

    fun validateImages(images: List<Image>): ErrorType? {
        if (images.isEmpty()) return ErrorType.EMPTY_IMAGES
        return null
    }
}
