package com.hogent.svkapp.features.create_ticket.domain.usecases

import com.hogent.svkapp.features.create_ticket.data.repositories.TicketRepository
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket

class CreateTicketUseCase(private val ticketRepository: TicketRepository) {
    fun execute(ticket: Ticket) {
        ticketRepository.addTicket(ticket)
    }
}
