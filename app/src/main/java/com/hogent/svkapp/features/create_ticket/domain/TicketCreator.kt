package com.hogent.svkapp.features.create_ticket.domain

import com.hogent.svkapp.features.create_ticket.data.repositories.TicketRepository
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket

class TicketCreator(private val ticketRepository: TicketRepository) {
    fun createTicket(routeNumber: Int, licensePlate: String) {
        val ticket = Ticket(routeNumber = routeNumber, licensePlate = licensePlate)
        ticketRepository.addTicket(ticket = ticket)
    }
}
