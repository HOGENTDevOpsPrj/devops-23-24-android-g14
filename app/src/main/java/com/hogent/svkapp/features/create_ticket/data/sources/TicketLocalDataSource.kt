package com.hogent.svkapp.features.create_ticket.data.sources

import android.util.Log
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket

class TicketLocalDataSource {
    private val tickets = mutableListOf<Ticket>()

    fun addTicket(ticket: Ticket) {
        tickets.add(element = ticket)

        // log ticket
        Log.d("Mock Database", "Added ticket: $ticket")
    }

    fun getTickets(): List<Ticket> {
        return tickets
    }
}
