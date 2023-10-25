package com.hogent.svkapp.data.repositories

import com.hogent.svkapp.data.sources.CargoTicketDataSource
import com.hogent.svkapp.domain.entities.CargoTicket
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CargoTicketRepositoryTest {
    private lateinit var cargoTicket: CargoTicket
    private lateinit var cargoTicketDataSource: CargoTicketDataSource
    private lateinit var cargoTicketRepository: CargoTicketRepository

    @Before
    fun setup() {
        cargoTicket = mock()
        cargoTicketDataSource = mock()
        cargoTicketRepository = CargoTicketRepository(cargoTicketDataSource)
    }

    @Test
    fun `addTicket should call addTicket on localDataSource`() {
        cargoTicketRepository.addCargoTicket(cargoTicket = cargoTicket)

        verify(cargoTicketDataSource).addCargoTicket(cargoTicket)
    }
}
