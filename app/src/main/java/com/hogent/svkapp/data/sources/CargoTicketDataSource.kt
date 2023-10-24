package com.hogent.svkapp.data.sources

import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.util.Logger

interface CargoTicketDataSource {
    fun addCargoTicket(cargoTicket: CargoTicket)
    fun getCargoTickets(): List<CargoTicket>
}

class LocalCargoTicketDataSource(private val logger: Logger) : CargoTicketDataSource {
    private val cargoTickets = mutableListOf<CargoTicket>()

    override fun addCargoTicket(cargoTicket: CargoTicket) {
        cargoTickets.add(cargoTicket)
        logger.debug(tag = "Local Database", message = "Added cargo ticket: $cargoTicket")
    }

    override fun getCargoTickets(): List<CargoTicket> = cargoTickets.toList()
}
