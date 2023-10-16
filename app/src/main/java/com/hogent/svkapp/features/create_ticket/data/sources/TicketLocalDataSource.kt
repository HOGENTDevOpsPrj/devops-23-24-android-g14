package com.hogent.svkapp.features.create_ticket.data.sources

import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import com.hogent.svkapp.main.util.Logger

class TicketLocalDataSource(private val logger: Logger) {
    private val tickets = mutableListOf<Ticket>()

    fun addTicket(ticket: Ticket) {
        tickets.add(element = ticket)

        logger.debug("Mock Database", "Added ticket: $ticket")
    }

    fun getTickets(): List<Ticket> {
        return tickets
    }
}
