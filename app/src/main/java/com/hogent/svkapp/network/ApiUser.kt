package com.hogent.svkapp.network

import kotlinx.serialization.Serializable

/**
 * A user. This class is used to send the user to the server.
 *
 * @property auth0Id the auth0 ID of the user.
 * @property name the name of the user.
 */
@Serializable
data class ApiUser(
    val auth0Id: String, val name: String
)
