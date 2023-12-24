package com.hogent.svkapp.data.repositories

import android.util.Log
import com.hogent.svkapp.domain.entities.User
import com.hogent.svkapp.network.ApiUser
import com.hogent.svkapp.network.UserApiService
import com.hogent.svkapp.util.CustomResult

/**
 * The errors that can occur when adding a [User] to the server.
 */
enum class UserApiRepositoryError {
    /**
     * The [User] is invalid.
     */
    INVALID_USER,

    /**
     * The server encountered an error.
     */
    SERVER_ERROR,

    /**
     * The user is not authenticated.
     */
    AUTHENTICATION_ERROR,

    /**
     * The [User] already exists.
     */
    ALREADY_EXISTS,

    /**
     * An unknown error occurred.
     */
    UNKNOWN_ERROR
}

/**
 * A repository for [User]s.
 */
interface UserApiRepository {
    /**
     * Adds a [User] to the repository.
     *
     * @param user the [User] to add.
     * @return a [CustomResult] with the result of the operation. If the operation was successful, the result is [Unit]. If the operation was unsuccessful, the result is a [UserApiRepositoryError].
     */
    suspend fun addUser(user: User): CustomResult<Unit, UserApiRepositoryError>
}

/**
 * A repository for [User]s. This repository saves the [User]s to the server.
 *
 * @property userApiService the [UserApiService] that is used to send the [User]s to the server.
 */
class UserApiRepositoryImpl(private val userApiService: UserApiService) : UserApiRepository {
    override suspend fun addUser(user: User): CustomResult<Unit, UserApiRepositoryError> {
        val apiUser = ApiUser(user.id, user.name)
        val response = userApiService.postUser(apiUser)
        return if (response.isSuccessful) {
            CustomResult.Success(Unit)
        } else {
            val message = response.errorBody()?.string() ?: response.message() ?: "Unknown error"

            Log.e(
                "UserApiRepository", when (response.code()) {
                    400 -> "Invalid user: $message"
                    401 -> "Authentication error: $message"
                    409 -> "User already exists: $message"

                    500 -> "Server error: $message"
                    else -> "Unknown error: ${response.code()} $message"
                }
            )

            when (response.code()) {
                400 -> CustomResult.Failure(UserApiRepositoryError.INVALID_USER)
                401 -> CustomResult.Failure(UserApiRepositoryError.AUTHENTICATION_ERROR)
                409 -> CustomResult.Failure(UserApiRepositoryError.ALREADY_EXISTS)
                500 -> CustomResult.Failure(UserApiRepositoryError.SERVER_ERROR)
                else -> CustomResult.Failure(UserApiRepositoryError.UNKNOWN_ERROR)
            }
        }
    }
}