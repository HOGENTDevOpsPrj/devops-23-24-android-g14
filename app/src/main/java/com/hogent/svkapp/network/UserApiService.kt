package com.hogent.svkapp.network

import retrofit2.http.Body
import retrofit2.http.POST

/**
 * A service for saving Users to the server.
 */
interface UserApiService {
    /**
     * Adds a User to the server.
     *
     * @param user the User to add.
     */
    @POST("users")
    suspend fun postUser(@Body user: ApiUser)
}