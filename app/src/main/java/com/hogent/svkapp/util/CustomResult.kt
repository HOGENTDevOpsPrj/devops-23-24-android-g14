package com.hogent.svkapp.util

/**
 * A result.
 *
 * @param T the type of the value.
 * @param E the type of the error.
 */
sealed class CustomResult<T, E> {
    /**
     * A success.
     *
     * @property value the value.
     * @param T the type of the value.
     * @param E the type of the error.
     */
    data class Success<T, E>(val value: T) : CustomResult<T, E>()

    /**
     * A failure.
     *
     * @property error the error.
     * @param T the type of the value.
     * @param E the type of the error.
     */
    data class Failure<T, E>(val error: E) : CustomResult<T, E>()
}
