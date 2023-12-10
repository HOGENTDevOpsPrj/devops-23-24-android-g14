package com.hogent.svkapp.network

import android.graphics.Bitmap
import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import java.io.ByteArrayOutputStream

interface FotoBlobStorageService {

    @Headers(
        "x-ms-blob-type: BlockBlob"
    )
    @PUT
    fun uploadImage(
        @Url url: String,
        @Body body: RequestBody
    ): Call<Void>
}



class AzureBlobUploader(private val baseUrl: String) {


    private val okHttpClient = OkHttpClient.Builder()
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val service: FotoBlobStorageService = retrofit.create(FotoBlobStorageService::class.java)

    fun uploadImage(blobName: String, sasToken: String, bitmap: Bitmap): String {
        val imageData = convertBitmapToRequestBody(bitmap)

        // Construct the full URL with query parameters
        val fullUrl = "${baseUrl}images/$blobName$sasToken"

        val call = service.uploadImage(fullUrl, imageData)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                // Handle successful response
                Log.d("SuccessImage", "SuccessImage")
                Log.d("Response Body", response.toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle failure
                Log.d("ErrorImage", "ErrorImage")
            }
        })
        return fullUrl
    }

    private fun convertBitmapToRequestBody(bitmap: Bitmap): RequestBody {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageData = outputStream.toByteArray()
        return RequestBody.create("image/png".toMediaTypeOrNull(), imageData)
    }
}


