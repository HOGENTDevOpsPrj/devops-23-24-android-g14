package com.hogent.svkapp.network

import com.hogent.svkapp.domain.entities.CargoTicket
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * A service for [CargoTicket]s.
 */
interface CargoTicketApiService {
    /**
     * post cargo ticket
     */
    @POST("cargo-tickets")
    suspend fun postCargoTicket(@Body cargoTicket: ApiCargoTicket)
}

