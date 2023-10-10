package com.hogent.svkapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hogent.svkapp.features.create_ticket.data.repositories.TicketRepositoryImpl
import com.hogent.svkapp.features.create_ticket.data.sources.TicketLocalDataSource
import com.hogent.svkapp.features.create_ticket.domain.usecases.CreateTicketUseCase
import com.hogent.svkapp.features.create_ticket.presentation.ui.CreateTicketScreen
import com.hogent.svkapp.features.create_ticket.presentation.viewmodels.CreateTicketViewModel
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateApplicationTheme {
                val ticketLocalDataSource = TicketLocalDataSource()
                val ticketRepository = TicketRepositoryImpl(ticketLocalDataSource)
                val createTicketUseCase = CreateTicketUseCase(ticketRepository)
                val viewModel = CreateTicketViewModel(createTicketUseCase)

                CreateTicketScreen(viewModel = viewModel)
            }
        }
    }
}
