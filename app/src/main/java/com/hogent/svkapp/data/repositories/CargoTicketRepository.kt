package com.hogent.svkapp.data.repositories

import com.hogent.svkapp.data.sources.CargoTicketDataSource
import com.hogent.svkapp.data.sources.LocalCargoTicketDataSource
import com.hogent.svkapp.domain.entities.CargoTicket

class CargoTicketRepository(private val cargoTicketDataSource: CargoTicketDataSource = LocalCargoTicketDataSource()) {
    fun addCargoTicket(cargoTicket: CargoTicket) = cargoTicketDataSource.addCargoTicket(cargoTicket)
}
