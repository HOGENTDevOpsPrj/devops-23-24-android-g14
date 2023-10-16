package com.hogent.svkapp.features.create_ticket.data.sources

import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import com.hogent.svkapp.main.util.NoOpLogger
import org.junit.Before
import org.junit.Test

class LocalTicketDataSourceTest {

    private lateinit var localTicketDataSource: LocalTicketDataSource

    @Before
    fun setup() {
        val logger = NoOpLogger()
        localTicketDataSource = LocalTicketDataSource(logger = logger)
    }

    @Test
    fun `when addTicket is called, ticket should be added`() {
        val ticket = Ticket(routeNumber = 1, licensePlate = "1-ABC-123", images = emptyList())

        localTicketDataSource.addTicket(ticket = ticket)

        assert(localTicketDataSource.getTickets().contains(element = ticket))
    }

    @Test
    fun `when getTickets is called, all tickets should be returned`() {
        val ticket1 = Ticket(routeNumber = 1, licensePlate = "1-ABC-123", images = emptyList())
        val ticket2 = Ticket(routeNumber = 2, licensePlate = "2-ABC-123", images = emptyList())
        val ticket3 = Ticket(routeNumber = 3, licensePlate = "3-ABC-123", images = emptyList())
        localTicketDataSource.addTicket(ticket = ticket1)
        localTicketDataSource.addTicket(ticket = ticket2)
        localTicketDataSource.addTicket(ticket = ticket3)

        val tickets = localTicketDataSource.getTickets()

        assert(tickets.contains(ticket1))
        assert(tickets.contains(ticket2))
        assert(tickets.contains(ticket3))
    }
}
