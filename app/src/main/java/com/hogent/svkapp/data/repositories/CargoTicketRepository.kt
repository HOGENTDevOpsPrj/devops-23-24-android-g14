package com.hogent.svkapp.data.repositories

import com.hogent.svkapp.data.sources.CargoTicketDataSource
import com.hogent.svkapp.data.sources.LocalCargoTicketDataSource
import com.hogent.svkapp.domain.entities.CargoTicket

/**
 * A repository for [CargoTicket]s.
 *
 * @property cargoTicketDataSource the [CargoTicketDataSource] that is used to store the [CargoTicket]s.
 */
class CargoTicketRepository(private val cargoTicketDataSource: CargoTicketDataSource = LocalCargoTicketDataSource()) {
    /**
     * Adds a [CargoTicket] to the repository.
     *
     * @param cargoTicket the [CargoTicket] to add.
     */

    // TODO: Make suspend function move to coroutine
    fun addCargoTicket(cargoTicket: CargoTicket): Unit =
        cargoTicketDataSource.addCargoTicket(cargoTicket)
}
