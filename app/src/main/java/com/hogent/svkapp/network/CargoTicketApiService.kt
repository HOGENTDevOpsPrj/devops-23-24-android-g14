package com.hogent.svkapp.network

import com.hogent.svkapp.domain.entities.CargoTicket
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * A service for [CargoTicket]s.
 */
interface CargoTicketApiService {

    @POST("cargo-tickets")
    suspend fun postCargoTicket(@Body cargoTicket: ApiCargoTicket)
}