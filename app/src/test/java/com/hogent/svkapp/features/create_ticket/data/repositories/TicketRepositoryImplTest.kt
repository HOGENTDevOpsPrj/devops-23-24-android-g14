package com.hogent.svkapp.features.create_ticket.data.repositories

import com.hogent.svkapp.features.create_ticket.data.sources.TicketDataSource
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class TicketRepositoryImplTest {
    private val dataSource: TicketDataSource = mock()

    private lateinit var repository: TicketRepositoryImpl

    @Before
    fun setUp() {
        repository = TicketRepositoryImpl(ticketDataSource = dataSource)
    }

    @Test
    fun `addTicket should call addTicket on localDataSource`() {
        val ticket = Ticket(routeNumber = 1, licensePlate = "1-ABC-123", images = emptyList())

        repository.addTicket(ticket = ticket)

        verify(dataSource).addTicket(ticket)
    }
}
