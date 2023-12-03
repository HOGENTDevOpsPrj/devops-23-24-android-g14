package com.hogent.svkapp.domain.entities

import android.util.Log

/**
 * An error that can occur when creating a [RouteNumber].
 */
enum class RouteNumberError {
    /**
     * The [RouteNumber] is empty.
     */
    Empty,

    /**
     * The [RouteNumber] is not a positive number.
     */
    NonPositiveNumber,

    /**
     * The [RouteNumber] has an invalid format.
     */
    InvalidFormat
}

/**
 * A route number.
 *
 * @property value the value of the route number.
 */
class RouteNumber private constructor(value: Int) {
    private var _value: Int = value
    val value: Int get() = _value

    companion object {
        /**
         * Creates a [RouteNumber].
         *
         * If the [routeNumber] is invalid, a [RouteNumberError] is returned. A route number is invalid
         * if it is empty, not a positive number or has an invalid format.
         *
         * @param routeNumber the value of the route number.
         * @return a [Result] containing either the [RouteNumber] or a [RouteNumberError].
         */
        fun create(routeNumber: String): Result<RouteNumber, List<RouteNumberError?>> {
            val result = validateStringRepresentation(routeNumber)
            Log.d("RouteNumberValidation", "Validation Result: $result")
            return if (result.isEmpty()) {
                Result.Success(RouteNumber(routeNumber.toInt()))
            } else {
                Result.Failure(result)
            }
        }

        /**
         * Validates the [routeNumber].
         *
         * @param routeNumber the value of the route number.
         *
         * @return a [RouteNumberError] if the [routeNumber] is invalid, null otherwise.
         */
        fun validateStringRepresentation(routeNumber: String): List<RouteNumberError?> {
            val cleanedRouteNumber = clean(routeNumber)

            if (cleanedRouteNumber.isEmpty()) return listOf(RouteNumberError.Empty)

            val intValue = cleanedRouteNumber.toIntOrNull() ?: return listOf(RouteNumberError.InvalidFormat)

            if (intValue <= 0) {
                return listOf(RouteNumberError.NonPositiveNumber)
            }

            return emptyList()
        }

        private fun clean(routeNumber: String): String {
            return routeNumber.trim().filter { !it.isWhitespace() }
        }
    }
}
