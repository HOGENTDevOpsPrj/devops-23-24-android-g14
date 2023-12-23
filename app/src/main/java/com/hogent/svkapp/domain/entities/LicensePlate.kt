package com.hogent.svkapp.domain.entities

/**
 * An error that can occur when validating a [LicensePlate].
 */
enum class LicensePlateError {
    /**
     * The [LicensePlate] is empty.
     */
    EMPTY,

    /**
     * The [LicensePlate] is too long.
     */
    TOO_LONG
}

/**
 * A license plate.
 *
 * @property value the value of the license plate.
 */
class LicensePlate private constructor(value: String) {
    private var _value: String = value
    val value: String get() = _value

    companion object {
        /**
         * Creates a [LicensePlate].
         *
         * If the [plate] is invalid, a [LicensePlateError] is returned. A license plate is invalid if it is empty or
         * too long.
         *
         * @param plate the value of the license plate.
         * @return a [Result] containing either the [LicensePlate] or a [LicensePlateError].
         */
        fun create(plate: String): Result<LicensePlate, LicensePlateError> {
            val result = validate(plate)

            return if (result == null) {
                Result.Success(LicensePlate(clean(plate)))
            } else {
                Result.Failure(result)
            }
        }

        /**
         * Validates the [plate].
         *
         * @param plate the value of the license plate.
         * @return a [LicensePlateError] if the [plate] is invalid, null otherwise.
         */
        fun validate(plate: String): LicensePlateError? {
            val cleanedPlate = clean(plate)

            if (cleanedPlate.isEmpty()) return LicensePlateError.EMPTY
            if (cleanedPlate.length >= 40) return LicensePlateError.TOO_LONG

            return null
        }

        private fun clean(plate: String): String {
            return plate.trim().filter { !it.isWhitespace() }.uppercase()
        }
    }
}
