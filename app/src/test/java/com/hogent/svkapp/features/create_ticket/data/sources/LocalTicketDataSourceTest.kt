package com.hogent.svkapp.features.create_ticket.data.sources

import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import com.hogent.svkapp.main.util.NoOpLogger
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LocalTicketDataSourceTest {

    private lateinit var localTicketDataSource: LocalTicketDataSource

    @Mock
    private lateinit var ticket1: Ticket

    @Mock
    private lateinit var ticket2: Ticket

    @Mock
    private lateinit var ticket3: Ticket

    @Before
    fun setUp() {
        val logger = NoOpLogger()
        MockitoAnnotations.openMocks(this)
        localTicketDataSource = LocalTicketDataSource(logger = logger)
    }

    @Test
    fun `when addTicket is called, ticket should be added`() {
        localTicketDataSource.addTicket(ticket = ticket1)

        assertTrue(localTicketDataSource.getTickets().contains(ticket1))
    }

    @Test
    fun `when getTickets is called, all tickets should be returned`() {
        localTicketDataSource.addTicket(ticket = ticket1)
        localTicketDataSource.addTicket(ticket = ticket2)
        localTicketDataSource.addTicket(ticket = ticket3)

        val tickets = localTicketDataSource.getTickets()

        assertTrue(tickets.contains(ticket1))
        assertTrue(tickets.contains(ticket2))
        assertTrue(tickets.contains(ticket3))
    }

    @Test
    fun `when no tickets are added, getTickets should return an empty list`() {
        assertTrue(localTicketDataSource.getTickets().isEmpty())
    }
}
