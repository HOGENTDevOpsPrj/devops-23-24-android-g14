package com.hogent.svkapp.features.create_ticket.data.sources

import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket

class TicketLocalDataSource {
    private val tickets = mutableListOf<Ticket>()

    fun addTicket(ticket: Ticket) {
        tickets.add(ticket)
    }
}
