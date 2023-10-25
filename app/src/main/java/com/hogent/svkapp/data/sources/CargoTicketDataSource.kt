package com.hogent.svkapp.data.sources

import com.hogent.svkapp.domain.entities.CargoTicket

interface CargoTicketDataSource {
    fun addCargoTicket(cargoTicket: CargoTicket)
    fun getCargoTickets(): List<CargoTicket>
}
