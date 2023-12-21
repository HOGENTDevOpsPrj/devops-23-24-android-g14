package com.hogent.svkapp.network

import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("users")
    suspend fun postUser(@Body user: ApiUser)
}