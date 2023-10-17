package com.hogent.svkapp.features.create_ticket.data.sources

import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import com.hogent.svkapp.main.util.Logger

interface TicketDataSource {
    fun addTicket(ticket: Ticket)
    fun getTickets(): List<Ticket>
}

class LocalTicketDataSource(private val logger: Logger) : TicketDataSource {
    private val tickets = mutableListOf<Ticket>()

    override fun addTicket(ticket: Ticket) {
        tickets.add(element = ticket)

        logger.debug(tag = "Local Database", message = "Added ticket: $ticket")
    }

    override fun getTickets(): List<Ticket> {
        return tickets.toList()
    }
}
