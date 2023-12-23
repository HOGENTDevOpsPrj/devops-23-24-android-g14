package com.hogent.svkapp.data.sources

import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.Logger
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class LocalDbCargoTicketDataSourceTest {

    private lateinit var logger: Logger
    private lateinit var cargoTicket1: CargoTicket
    private lateinit var cargoTicket2: CargoTicket
    private lateinit var cargoTicket3: CargoTicket
    private lateinit var localTicketDataSource: LocalCargoTicketDataSource

    @Before
    fun setUp() {
        logger = mock()
        cargoTicket1 = mock()
        cargoTicket2 = mock()
        cargoTicket3 = mock()
        localTicketDataSource = LocalCargoTicketDataSource(logger = logger)
    }

    @Test
    suspend fun `when addTicket is called, ticket should be added`() {
        localTicketDataSource.addCargoTicket(cargoTicket = cargoTicket1)

        assertTrue(localTicketDataSource.getCargoTickets().contains(cargoTicket1))
    }

    @Test
    suspend fun `when addTicket is called, ticket should be logged`() {
        localTicketDataSource.addCargoTicket(cargoTicket = cargoTicket1)

        verify(logger).debug(tag = "Local Database", message = "Added cargo ticket: $cargoTicket1")
    }

    @Test
    suspend fun `when getTickets is called, all tickets should be returned`() {
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
