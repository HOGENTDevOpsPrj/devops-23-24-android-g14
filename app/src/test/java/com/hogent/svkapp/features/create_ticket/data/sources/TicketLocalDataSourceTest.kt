package com.hogent.svkapp.features.create_ticket.data.sources

import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import org.junit.Before
import org.junit.Test

class TicketLocalDataSourceTest {

    private lateinit var ticketLocalDataSource: TicketLocalDataSource

    @Before
    fun setup() {
        ticketLocalDataSource = TicketLocalDataSource()
    }

    @Test
    fun `when addTicket is called, ticket should be added`() {
        val ticket = Ticket(routeNumber = 1, licensePlate = "1-ABC-123")

        ticketLocalDataSource.addTicket(ticket)

        assert(ticketLocalDataSource.getTickets().contains(ticket))
    }

    @Test
    fun `when getTickets is called, all tickets should be returned`() {
        val ticket1 = Ticket(routeNumber = 1, licensePlate = "1-ABC-123")
        val ticket2 = Ticket(routeNumber = 2, licensePlate = "2-ABC-123")
        val ticket3 = Ticket(routeNumber = 3, licensePlate = "3-ABC-123")
        ticketLocalDataSource.addTicket(ticket1)
        ticketLocalDataSource.addTicket(ticket2)
        ticketLocalDataSource.addTicket(ticket3)

        val tickets = ticketLocalDataSource.getTickets()

        assert(tickets.contains(ticket1))
        assert(tickets.contains(ticket2))
        assert(tickets.contains(ticket3))
    }
}
