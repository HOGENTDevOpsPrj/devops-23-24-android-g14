package com.hogent.svkapp.network
import com.hogent.svkapp.domain.entities.CargoTicket
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


private const val BASE_URL =
    "https://localhost:5001/api/"

private val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL)
    .build()
interface LadingApiService {
    @POST("lading/new")
    fun createLading(@Body vararg cargoTicket: CargoTicket) : Call<ResponseBody>

}

object LadingApi {
    val retrofitService : LadingApiService by lazy {
        retrofit.create(LadingApiService::class.java)
    }
}