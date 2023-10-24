package com.hogent.svkapp.features.create_ticket.data.sources

import com.hogent.svkapp.data.sources.LocalCargoTicketDataSource
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.util.NoOpLogger
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LocalCargoCargoTicketDataSourceTest {

    private lateinit var localTicketDataSource: LocalCargoTicketDataSource

    @Mock
    private lateinit var cargoTicket1: CargoTicket

    @Mock
    private lateinit var cargoTicket2: CargoTicket

    @Mock
    private lateinit var cargoTicket3: CargoTicket

    @Before
    fun setUp() {
        val logger = NoOpLogger()
        MockitoAnnotations.openMocks(this)
        localTicketDataSource = LocalCargoTicketDataSource(logger = logger)
    }

    @Test
    fun `when addTicket is called, ticket should be added`() {
        localTicketDataSource.addCargoTicket(cargoTicket = cargoTicket1)

        assertTrue(localTicketDataSource.getCargoTickets().contains(cargoTicket1))
    }

    @Test
    fun `when getTickets is called, all tickets should be returned`() {
        localTicketDataSource.addCargoTicket(cargoTicket = cargoTicket1)
        localTicketDataSource.addCargoTicket(cargoTicket = cargoTicket2)
        localTicketDataSource.addCargoTicket(cargoTicket = cargoTicket3)

        val tickets = localTicketDataSource.getCargoTickets()

        assertTrue(tickets.contains(cargoTicket1))
        assertTrue(tickets.contains(cargoTicket2))
        assertTrue(tickets.contains(cargoTicket3))
    }

    @Test
    fun `when no tickets are added, getTickets should return an empty list`() {
        assertTrue(localTicketDataSource.getCargoTickets().isEmpty())
    }
}
