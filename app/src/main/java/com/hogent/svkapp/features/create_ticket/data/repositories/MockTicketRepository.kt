package com.hogent.svkapp.features.create_ticket.data.repositories

import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket

class MockTicketRepository : TicketRepository {
    override fun addTicket(ticket: Ticket) {}
}
