package com.hogent.svkapp.features.create_ticket.data.repositories

import com.hogent.svkapp.data.repositories.CargoTicketRepositoryImpl
import com.hogent.svkapp.data.sources.CargoTicketDataSource
import com.hogent.svkapp.domain.entities.CargoTicket
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CargoCargoTicketRepositoryImplTest {
    @Mock
    private lateinit var dataSource: CargoTicketDataSource

    @Mock
    private lateinit var cargoTicket: CargoTicket

    private lateinit var repository: CargoTicketRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = CargoTicketRepositoryImpl(cargoTicketDataSource = dataSource)
    }

    @Test
    fun `addTicket should call addTicket on localDataSource`() {
        repository.addCargoTicket(cargoTicket = cargoTicket)

        verify(dataSource).addCargoTicket(cargoTicket)
    }
}
