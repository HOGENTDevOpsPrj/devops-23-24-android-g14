package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.util.CustomResult

/**
 * An error that can occur when creating a [RouteNumber].
 */
enum class RouteNumberError {
    /**
     * The [RouteNumber] is empty.
     */
    EMPTY,

    /**
     * The [RouteNumber] is not a positive number.
     */
    NON_POSITIVE_NUMBER,

    /**
     * The [RouteNumber] has an invalid format.
     */
    INVALID_FORMAT
}

/**
 * A route number.
 *
 * @property value the value of the route number.
 */
class RouteNumber private constructor(value: String) {
    private var _value: String = value
    val value: String get() = _value

    companion object {
        /**
         * Creates a [RouteNumber].
         *
         * If the [routeNumber] is invalid, a [RouteNumberError] is returned. A route number is invalid
         * if it is empty, not a positive number or has an invalid format.
         *
         * @param routeNumber the value of the route number.
         * @return a [CustomResult] containing either the [RouteNumber] or a [RouteNumberError].
         */
        fun create(routeNumber: String): CustomResult<RouteNumber, List<RouteNumberError?>> {
            val result = validateStringRepresentation(routeNumber)
            return if (result.isEmpty()) {
                CustomResult.Success(RouteNumber(routeNumber))
            } else {
                CustomResult.Failure(result)
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

            if (cleanedRouteNumber.isEmpty()) return listOf(RouteNumberError.EMPTY)

            val intValue = cleanedRouteNumber.toIntOrNull() ?: return listOf(RouteNumberError.INVALID_FORMAT)

            if (intValue <= 0) {
                return listOf(RouteNumberError.NON_POSITIVE_NUMBER)
            }

            return emptyList()
        }

        private fun clean(routeNumber: String): String {
            return routeNumber.trim().filter { !it.isWhitespace() }
        }
    }
}
