package com.hogent.svkapp.data.repositories

import com.hogent.svkapp.data.sources.CargoTicketDataSource
import com.hogent.svkapp.data.sources.LocalCargoTicketDataSource
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.network.CargoTicketApiService


interface CargoTicketRepository {
    suspend fun addCargoTicket(cargoTicket: CargoTicket)
}

/**
 * A repository for [CargoTicket]s.
 *
 * @property cargoTicketDataSource the [CargoTicketDataSource] that is used to store the [CargoTicket]s.
 */
class LocalCargoTicketRepository(
    private val cargoTicketDataSource: CargoTicketDataSource = LocalCargoTicketDataSource
        ()
) : CargoTicketRepository {
    /**
     * Adds a [CargoTicket] to the repository.
     *
     * @param cargoTicket the [CargoTicket] to add.
     */

    // TODO: Make suspend function move to coroutine
    override suspend fun addCargoTicket(cargoTicket: CargoTicket): Unit =
        cargoTicketDataSource.addCargoTicket(cargoTicket)
}

class ApiCargoTicketRepository(
    private val cargoTicketApiService: CargoTicketApiService
) : CargoTicketRepository {
    /**
     * Adds a [CargoTicket] to the repository.
     *
     * @param cargoTicket the [CargoTicket] to add.
     */
    override suspend fun addCargoTicket(cargoTicket: CargoTicket) {
        cargoTicketApiService.postCargoTicket(cargoTicket)
    }
}
