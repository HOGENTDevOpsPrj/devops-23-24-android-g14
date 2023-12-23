package com.hogent.svkapp.network

import android.graphics.Bitmap
import android.util.Log
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
import retrofit2.http.PUT
import retrofit2.http.Url
import java.io.ByteArrayOutputStream

/**
 * A service for storing images in blob storage.
 */
interface ImageBlobStorageService {

    /**
     * Uploads an image to the server.
     */
    @Headers(
        "x-ms-blob-type: BlockBlob"
    )
    @PUT
    fun uploadImage(
        @Url url: String, @Body body: RequestBody
    ): Call<Void>
}


/**
 * An uploader for images. This class is used to upload images to the server.
 *
 * @property baseUrl the base URL of the server.
 */
class AzureBlobUploader(private val baseUrl: String) {
    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ScalarsConverterFactory.create()).client(okHttpClient)
            .build()

    private val service: ImageBlobStorageService = retrofit.create(ImageBlobStorageService::class.java)

    /**
     * Uploads an image to the server.
     *
     * @param blobName the name of the blob.
     * @param sasToken the SAS token of the blob.
     * @param bitmap the bitmap of the image.
     * @return the URL of the image.
     */
    fun uploadImage(blobName: String, sasToken: String, bitmap: Bitmap): String {
        val imageData = convertBitmapToRequestBody(bitmap)

        // Construct the full URL with query parameters
        val fullUrl = "${baseUrl}images/$blobName$sasToken"

        val call = service.uploadImage(fullUrl, imageData)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (Log.isLoggable("Response", Log.DEBUG)) {
                    Log.d("Response", response.toString())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                if (Log.isLoggable("Response", Log.DEBUG)) {
                    Log.d("Response", t.toString())
                }
            }
        })
        return fullUrl
    }

    private fun convertBitmapToRequestBody(bitmap: Bitmap): RequestBody {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageData = outputStream.toByteArray()
        return imageData.toRequestBody("image/png".toMediaTypeOrNull(), 0, imageData.size)
    }
}

