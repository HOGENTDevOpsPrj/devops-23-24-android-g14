package com.hogent.svkapp.features.create_ticket

import com.hogent.svkapp.features.create_ticket.data.repositories.MockTicketRepository
import com.hogent.svkapp.features.create_ticket.data.repositories.TicketRepository
import com.hogent.svkapp.features.create_ticket.data.repositories.TicketRepositoryImpl
import com.hogent.svkapp.features.create_ticket.data.sources.TicketLocalDataSource
import com.hogent.svkapp.features.create_ticket.domain.TicketCreator
import com.hogent.svkapp.features.create_ticket.domain.Validator
import com.hogent.svkapp.features.create_ticket.presentation.viewmodels.CreateTicketViewModel

interface CreateTicketModule {
    fun getViewModel(): CreateTicketViewModel
}

class CreateTicketModuleImpl : CreateTicketModule {
    private val ticketLocalDataSource = TicketLocalDataSource()
    private val ticketRepository: TicketRepository =
        TicketRepositoryImpl(localDataSource = ticketLocalDataSource)
    private val ticketCreator = TicketCreator(ticketRepository = ticketRepository)
    private val validator = Validator()
    private val viewModel = CreateTicketViewModel(
        validator = validator, ticketCreator = ticketCreator
    )

    override fun getViewModel(): CreateTicketViewModel {
        return viewModel
    }
}

class MockCreateTicketModule : CreateTicketModule {
    private val ticketRepository: TicketRepository = MockTicketRepository()
    private val ticketCreator = TicketCreator(ticketRepository = ticketRepository)
    private val validator = Validator()
    private val viewModel = CreateTicketViewModel(
        validator = validator, ticketCreator = ticketCreator
    )

    override fun getViewModel(): CreateTicketViewModel {
        return viewModel
    }
}