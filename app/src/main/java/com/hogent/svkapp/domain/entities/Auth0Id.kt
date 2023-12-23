package com.hogent.svkapp.domain.entities

/**
 * An Auth0 ID.
 */
enum class Auth0IdError {
    /**
     * The [Auth0Id] is empty.
     */
    EMPTY
}

/**
 * An Auth0 ID. This is the ID that Auth0 uses to identify a user.
 */
class Auth0Id private constructor(value: String) {
    private var _value: String = value

    /**
     * The value of the [Auth0Id].
     */
    val value: String get() = _value

    companion object {
        /**
         * Creates an [Auth0Id]. If the [value] is invalid, an [Auth0IdError] is returned. An [Auth0Id] is invalid if
         * it is empty.
         *
         * @param value the value of the [Auth0Id].
         * @return a [Result] containing either the [Auth0Id] or an [Auth0IdError].
         */
        fun create(value: String): Result<Auth0Id, Auth0IdError> {
            val result = validate(value)

            return if (result == null) {
                Result.Success(Auth0Id(clean(value)))
            } else {
                Result.Failure(result)
            }
        }

        /**
         * Validates the [value].
         *
         * @param value the value of the [Auth0Id].
         * @return an [Auth0IdError] if the [value] is invalid, null otherwise.
         */
        fun validate(value: String): Auth0IdError? {
            val cleanedValue = clean(value)

            return when {
                cleanedValue.isEmpty() -> Auth0IdError.EMPTY
                else -> null
            }
        }

        private fun clean(value: String): String {
            return value.trim()
        }
    }
}