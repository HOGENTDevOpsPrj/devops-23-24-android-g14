package com.hogent.svkapp.network

import android.graphics.Bitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.ByteArrayOutputStream

interface FotoBlobStorageService {

    @Headers(
        "Content-Type: image/jpeg",
        "x-ms-blob-type: BlockBlob"
    )
    @PUT("images/{blobName}/{sasToken}")
    fun uploadImage(
        @Path("blobName") blobName: String,
        @Query("sasToken") sasToken: String,
        @Body body: RequestBody
    ): Call<Void>
}

class AzureBlobUploader(private val baseUrl: String) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private val service: FotoBlobStorageService = retrofit.create(FotoBlobStorageService::class.java)

    fun uploadImage( blobName: String, sasToken: String, bitmap: Bitmap): String {
        val imageData = convertBitmapToRequestBody(bitmap)

        val call = service.uploadImage(blobName, sasToken, imageData)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                // Handle successful response
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle failure
            }
        })
        return "${baseUrl}/images/${blobName}";
    }

    private fun convertBitmapToRequestBody(bitmap: Bitmap): RequestBody {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val imageData = outputStream.toByteArray()
        return imageData.toRequestBody("image/jpeg".toMediaTypeOrNull())
    }
}