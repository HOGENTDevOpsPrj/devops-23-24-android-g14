package com.hogent.svkapp.features.create_ticket.data.repositories

import com.hogent.svkapp.features.create_ticket.data.sources.TicketDataSource
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TicketRepositoryImplTest {
    @Mock
    private lateinit var dataSource: TicketDataSource

    @Mock
    private lateinit var ticket: Ticket

    private lateinit var repository: TicketRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = TicketRepositoryImpl(ticketDataSource = dataSource)
    }

    @Test
    fun `addTicket should call addTicket on localDataSource`() {
        repository.addTicket(ticket = ticket)

        verify(dataSource).addTicket(ticket)
    }
}
