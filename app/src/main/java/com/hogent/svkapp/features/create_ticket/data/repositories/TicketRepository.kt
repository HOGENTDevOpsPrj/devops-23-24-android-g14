package com.hogent.svkapp.features.create_ticket.data.repositories

import com.hogent.svkapp.features.create_ticket.data.sources.TicketLocalDataSource
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket

interface TicketRepository {
    fun addTicket(ticket: Ticket)
}

class TicketRepositoryImpl(private val localDataSource: TicketLocalDataSource) : TicketRepository {
    override fun addTicket(ticket: Ticket) {
        localDataSource.addTicket(ticket = ticket)
    }
}

class MockTicketRepository : TicketRepository {
    override fun addTicket(ticket: Ticket) {}
}
