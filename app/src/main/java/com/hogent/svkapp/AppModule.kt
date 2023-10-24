package com.hogent.svkapp

import com.hogent.svkapp.data.repositories.CargoTicketRepository
import com.hogent.svkapp.data.repositories.CargoTicketRepositoryImpl
import com.hogent.svkapp.data.repositories.MockCargoTicketRepository
import com.hogent.svkapp.data.sources.LocalCargoTicketDataSource
import com.hogent.svkapp.domain.Validator
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel
import com.hogent.svkapp.util.AndroidLogger

interface AppModule {
    fun getMainScreenViewModel(): MainScreenViewModel
}

class AppModuleImpl : AppModule {
    private val logger = AndroidLogger()
    private val ticketDataSource = LocalCargoTicketDataSource(logger = logger)
    private val cargoTicketRepository: CargoTicketRepository =
        CargoTicketRepositoryImpl(cargoTicketDataSource = ticketDataSource)
    private val validator = Validator()
    private val mainScreenViewModel = MainScreenViewModel(
        validator = validator, cargoTicketRepository = cargoTicketRepository
    )

    override fun getMainScreenViewModel(): MainScreenViewModel {
        return mainScreenViewModel
    }
}

class MockAppModule : AppModule {
    private val cargoTicketRepository: CargoTicketRepository = MockCargoTicketRepository()
    private val validator = Validator()
    private val mainScreenViewModel = MainScreenViewModel(
        validator = validator, cargoTicketRepository = cargoTicketRepository
    )

    override fun getMainScreenViewModel(): MainScreenViewModel {
        return mainScreenViewModel
    }
}
