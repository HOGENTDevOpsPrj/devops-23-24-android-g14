package com.hogent.svkapp.data.sources

import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.AndroidLogger
import com.hogent.svkapp.domain.entities.Logger

/**
 * A local (in-memory) data source for [CargoTicket]s.
 *
 * @property logger the [Logger] that is used to log messages.
 */
class LocalCargoTicketDataSource(private val logger: Logger = AndroidLogger()) :
    CargoTicketDataSource {
    private val cargoTickets = mutableListOf<CargoTicket>()

    override suspend fun addCargoTicket(cargoTicket: CargoTicket) {
        cargoTickets.add(cargoTicket)
        logger.debug(tag = "Local Database", message = "Added cargo ticket: $cargoTicket")
    }

    override fun getCargoTickets(): List<CargoTicket> = cargoTickets.toList()
}