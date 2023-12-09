package com.hogent.svkapp.domain.entities

import androidx.room.TypeConverter

/**
 * An error that can occur when validating a [RouteNumber].
 */
enum class RouteNumberCollectionError {
    /**
     * The [RouteNumberCollection] is empty.
     */
    Empty
}

/**
 * A collection of [RouteNumber]s.
 *
 * @property value the [RouteNumber]s in the collection.
 */
class RouteNumberCollection private constructor(value: List<RouteNumber>) {
    private var _value: List<RouteNumber> = value
    val value: List<RouteNumber> get() = _value

    companion object {
        /**
         * Creates a [RouteNumberCollection].
         *
         * An [RouteNumberCollectionError.Empty] is returned if the [routeNumbers] are empty.
         *
         * @param routeNumbers the [RouteNumber]s in the collection.
         * @return a [Result] containing either the [RouteNumberCollection] or a [RouteNumberCollectionError].
         */
        fun create(routeNumbers: List<RouteNumber>): Result<RouteNumberCollection, RouteNumberCollectionError> {
            return if (routeNumbers.isEmpty()) {
                Result.Failure(RouteNumberCollectionError.Empty)
            } else {
                Result.Success(RouteNumberCollection(routeNumbers))
            }
        }

        /**
         * Validates the [RouteNumber]s.
         *
         * @param routeNumbers the [RouteNumber]s to validate.
         * @return an [RouteNumberCollectionError] if the [RouteNumber]s are invalid, null otherwise.
         */
        fun validateStringRepresentations(routeNumbers: List<String>):
                Result<List<List<RouteNumberError?>>, RouteNumberCollectionError> {
            val routeNumberResults =
                routeNumbers.map { RouteNumber.validateStringRepresentation(it) }
            return if (routeNumberResults.isEmpty()) {
                Result.Failure(RouteNumberCollectionError.Empty)
            } else {
                Result.Success(routeNumberResults)
            }
        }
    }
}

class RouteNumberCollectionConverter {
    @TypeConverter
    fun storedStringToRouteNumberCollection(value: String): RouteNumberCollection {
        val routeNumbers: List<RouteNumber> = value
            .split(";")
            .map {
                RouteNumber.create(it) as Result.Success
            }.map { it.value }
        return (RouteNumberCollection.create(routeNumbers) as Result.Success).value
    }

    @TypeConverter
    fun routeNumberCollectionToStoredString(routeNumberCollection: RouteNumberCollection): String {
        return routeNumberCollection.value.joinToString(";") { it.value.toString() }
    }
}
