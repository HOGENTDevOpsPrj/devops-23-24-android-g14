package com.hogent.svkapp.domain.entities

/**
 * A result.
 *
 * @param T the type of the value.
 * @param E the type of the error.
 */
sealed class Result<T, E> {
    /**
     * A success.
     *
     * @property value the value.
     * @param T the type of the value.
     * @param E the type of the error.
     */
    data class Success<T, E>(val value: T) : Result<T, E>()

    /**
     * A failure.
     *
     * @property error the error.
     * @param T the type of the value.
     * @param E the type of the error.
     */
    data class Failure<T, E>(val error: E) : Result<T, E>()
}
