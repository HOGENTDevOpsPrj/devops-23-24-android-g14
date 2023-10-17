package com.hogent.svkapp.features.create_ticket.domain

import com.hogent.svkapp.features.create_ticket.data.repositories.TicketRepository
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import com.hogent.svkapp.features.create_ticket.domain.entities.Image

class TicketCreator(private val ticketRepository: TicketRepository) {
    fun createTicket(routeNumber: Int, licensePlate: String, images: List<Image>) {
        val ticket = Ticket(routeNumber = routeNumber, licensePlate = licensePlate, images = images)
        ticketRepository.addTicket(ticket = ticket)
    }
}
