package com.hogent.svkapp.features.create_ticket.data.repositories

import com.hogent.svkapp.features.create_ticket.data.sources.TicketLocalDataSource
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class TicketRepositoryImplTest {
    private val localDataSource: TicketLocalDataSource = mock()

    private lateinit var repository: TicketRepositoryImpl

    @Before
    fun setUp() {
        repository = TicketRepositoryImpl(localDataSource)
    }

    @Test
    fun `addTicket should call addTicket on localDataSource`() {
        val ticket = Ticket(routeNumber = 1, licensePlate = "1-ABC-123")

        repository.addTicket(ticket)

        verify(localDataSource).addTicket(ticket)
    }
}
