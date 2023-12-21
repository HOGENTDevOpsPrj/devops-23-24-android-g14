package com.hogent.svkapp.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiUser (
    val auth0Id: String,
    val name: String
)

