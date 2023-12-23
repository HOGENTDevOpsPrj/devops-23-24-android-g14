package com.hogent.svkapp.network

import com.hogent.svkapp.domain.entities.CargoTicket
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * A service for [CargoTicket]s.
 */
interface CargoTicketApiService {

    /**
     * Adds a [CargoTicket] to the server.
     *
     * @param cargoTicket the [CargoTicket] to add.
     */
    @POST("cargo-tickets")
    suspend fun postCargoTicket(@Body cargoTicket: ApiCargoTicket)
}