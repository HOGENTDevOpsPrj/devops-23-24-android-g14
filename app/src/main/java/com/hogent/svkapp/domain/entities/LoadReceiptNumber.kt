package com.hogent.svkapp.domain.entities

/**
 * An error that can occur when a [LoadReceiptNumber] is invalid.
 */
enum class LoadReceiptNumberError {
    /**
     * The [LoadReceiptNumber] is empty.
     */
    EMPTY,

    /**
     * The [LoadReceiptNumber] is invalid.
     */
    INVALID
}

/**
 * A load receipt number. This is a number that is used to identify a load receipt.
 */
class LoadReceiptNumber private constructor(value: String) {
    private var _value: String = value

    /**
     * The value of the [LoadReceiptNumber].
     */
    val value: String get() = _value

    companion object {
        /**
         * Creates a [LoadReceiptNumber]. If the [value] is invalid, a [LoadReceiptNumberError] is returned. A
         * [LoadReceiptNumber] is invalid if it is empty or is not a positive integer with 10 digits starting with 1004.
         *
         * @param value the value of the [LoadReceiptNumber].
         * @return a [Result] containing either the [LoadReceiptNumber] or a [LoadReceiptNumberError].
         */
        fun create(value: String): Result<LoadReceiptNumber, LoadReceiptNumberError> {
            val result = validate(value)

            return if (result == null) {
                Result.Success(LoadReceiptNumber(clean(value)))
            } else {
                Result.Failure(result)
            }
        }

        /**
         * Validates the [value]. A [LoadReceiptNumber] is invalid if it is empty or is not a positive integer with
         * 10 digits starting with 1004.
         *
         * @param value the value of the [LoadReceiptNumber].
         * @return a [LoadReceiptNumberError] if the [value] is invalid, null otherwise.
         */
        fun validate(value: String): LoadReceiptNumberError? {
            val cleanedValue = clean(value)

            val regex = Regex("^1004[0-9]{6}$")

            return when {
                cleanedValue.isEmpty() -> LoadReceiptNumberError.EMPTY
                !regex.matches(cleanedValue) -> LoadReceiptNumberError.INVALID
                else -> null
            }
        }

        private fun clean(value: String): String {
            return value.trim()
        }
    }
}