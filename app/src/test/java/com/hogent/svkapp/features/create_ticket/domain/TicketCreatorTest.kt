package com.hogent.svkapp.features.create_ticket.domain

import com.hogent.svkapp.features.create_ticket.data.repositories.TicketRepository
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.eq

class TicketCreatorTest {
    private lateinit var ticketRepository: TicketRepository
    private lateinit var ticketCreator: TicketCreator

    @Before
    fun setUp() {
        ticketRepository = mock()
        ticketCreator = TicketCreator(ticketRepository)
    }

    @Test
    fun `createTicket creates a ticket and adds to repository`() {
        val routeNumber = 123
        val licensePlate = "1-ABC-123"

        ticketCreator.createTicket(routeNumber = routeNumber, licensePlate = licensePlate)

        val expectedTicket = Ticket(routeNumber = routeNumber, licensePlate = licensePlate)
        verify(ticketRepository).addTicket(ticket = eq(expectedTicket))
    }
}