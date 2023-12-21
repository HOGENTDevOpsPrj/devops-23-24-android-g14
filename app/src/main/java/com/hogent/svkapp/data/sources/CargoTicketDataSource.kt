package com.hogent.svkapp.data.sources

import com.hogent.svkapp.domain.entities.CargoTicket

/**
 * A data source for [CargoTicket]s.
 */
interface CargoTicketDataSource {
    /**
     * Adds a [CargoTicket] to the data source.
     *
     * @param cargoTicket the [CargoTicket] to add.
     */
    suspend fun addCargoTicket(cargoTicket: CargoTicket)

    /**
     * Gets all [CargoTicket]s from the data source.
     *
     * @return a list of [CargoTicket]s.
     */
    fun getCargoTickets(): List<CargoTicket>
}