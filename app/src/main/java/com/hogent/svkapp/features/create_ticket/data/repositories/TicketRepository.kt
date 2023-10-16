package com.hogent.svkapp.features.create_ticket.data.repositories

import com.hogent.svkapp.features.create_ticket.data.sources.TicketDataSource
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket

interface TicketRepository {
    fun addTicket(ticket: Ticket)
}

class TicketRepositoryImpl(private val ticketDataSource: TicketDataSource) : TicketRepository {
    override fun addTicket(ticket: Ticket) {
        ticketDataSource.addTicket(ticket = ticket)
    }
}

class MockTicketRepository : TicketRepository {
    override fun addTicket(ticket: Ticket) {}
}
