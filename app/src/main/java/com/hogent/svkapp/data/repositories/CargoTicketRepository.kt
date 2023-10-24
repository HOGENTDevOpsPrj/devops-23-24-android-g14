package com.hogent.svkapp.data.repositories

import com.hogent.svkapp.data.sources.CargoTicketDataSource
import com.hogent.svkapp.data.sources.LocalCargoTicketDataSource
import com.hogent.svkapp.domain.entities.CargoTicket

interface CargoTicketRepository {
    fun addCargoTicket(cargoTicket: CargoTicket)
}

class CargoTicketRepositoryImpl(private val cargoTicketDataSource: CargoTicketDataSource = LocalCargoTicketDataSource()) :
    CargoTicketRepository {
    override fun addCargoTicket(cargoTicket: CargoTicket) =
        cargoTicketDataSource.addCargoTicket(cargoTicket)
}
