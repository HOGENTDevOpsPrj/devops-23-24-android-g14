package com.hogent.svkapp.data

import android.content.Context
import com.hogent.svkapp.data.repositories.ApiCargoTicketRepository
import com.hogent.svkapp.data.repositories.CargoTicketRepository
import com.hogent.svkapp.network.CargoTicketApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val cargoTicketRepository: CargoTicketRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val BASE_URL = "https://10.0.2.2:5001/api/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(BASE_URL)
        .build()

    private val retrofitCargoTicketService: CargoTicketApiService by lazy {
        retrofit.create(CargoTicketApiService::class.java)
    }

    override val cargoTicketRepository: CargoTicketRepository by lazy {
        ApiCargoTicketRepository(retrofitCargoTicketService)
    }
}