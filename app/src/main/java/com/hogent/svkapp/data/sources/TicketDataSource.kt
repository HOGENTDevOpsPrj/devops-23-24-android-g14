package com.hogent.svkapp.data.sources

import com.hogent.svkapp.domain.entities.Ticket
import com.hogent.svkapp.util.Logger

interface TicketDataSource {
    fun addTicket(ticket: Ticket)
    fun getTickets(): List<Ticket>
}

class LocalTicketDataSource(private val logger: Logger) : TicketDataSource {
    private val tickets = mutableListOf<Ticket>()

    override fun addTicket(ticket: Ticket) {
        tickets.add(ticket)
        logger.debug(tag = "Local Database", message = "Added ticket: $ticket")
    }

    override fun getTickets(): List<Ticket> = tickets.toList()
}
