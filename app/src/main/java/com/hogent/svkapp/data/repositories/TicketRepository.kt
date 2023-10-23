package com.hogent.svkapp.data.repositories

import com.hogent.svkapp.data.sources.TicketDataSource
import com.hogent.svkapp.domain.entities.Ticket

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
