package com.hogent.svkapp.network

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface FotoBlobStorageService {

    @Headers(
        "Content-Type: image/png",
        "x-ms-blob-type: BlockBlob"
    )
    @PUT("images/{blobName}")
    fun uploadImage(
        @Path("blobName") blobName: String,
        @Body body: RequestBody
    ): Call<Void>
}