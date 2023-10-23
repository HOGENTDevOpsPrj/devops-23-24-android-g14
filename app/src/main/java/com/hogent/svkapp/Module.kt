package com.hogent.svkapp

import com.hogent.svkapp.data.repositories.MockTicketRepository
import com.hogent.svkapp.data.repositories.TicketRepository
import com.hogent.svkapp.data.repositories.TicketRepositoryImpl
import com.hogent.svkapp.data.sources.LocalTicketDataSource
import com.hogent.svkapp.domain.Validator
import com.hogent.svkapp.presentation.viewmodels.CreateTicketScreenViewModel
import com.hogent.svkapp.util.AndroidLogger

interface CreateTicketModule {
    fun getViewModel(): CreateTicketScreenViewModel
}

class CreateTicketModuleImpl : CreateTicketModule {
    private val logger = AndroidLogger()
    private val ticketDataSource = LocalTicketDataSource(logger = logger)
    private val ticketRepository: TicketRepository =
        TicketRepositoryImpl(ticketDataSource = ticketDataSource)
    private val validator = Validator()
    private val viewModel = CreateTicketScreenViewModel(
        validator = validator, ticketRepository = ticketRepository
    )

    override fun getViewModel(): CreateTicketScreenViewModel {
        return viewModel
    }
}

class MockCreateTicketModule : CreateTicketModule {
    private val ticketRepository: TicketRepository = MockTicketRepository()
    private val validator = Validator()
    private val viewModel = CreateTicketScreenViewModel(
        validator = validator, ticketRepository = ticketRepository
    )

    override fun getViewModel(): CreateTicketScreenViewModel {
        return viewModel
    }
}
