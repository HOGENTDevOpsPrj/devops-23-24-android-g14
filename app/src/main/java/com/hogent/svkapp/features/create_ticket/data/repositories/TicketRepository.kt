package com.hogent.svkapp.features.create_ticket.data.repositories

import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket

interface TicketRepository {
    fun addTicket(ticket: Ticket)
}
