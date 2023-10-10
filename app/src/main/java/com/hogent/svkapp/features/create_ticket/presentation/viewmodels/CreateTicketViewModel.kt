package com.hogent.svkapp.features.create_ticket.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import com.hogent.svkapp.features.create_ticket.domain.usecases.CreateTicketUseCase

class CreateTicketViewModel(private val createTicketUseCase: CreateTicketUseCase) : ViewModel() {
    val routeNumber = mutableStateOf("")
    val licensePlate = mutableStateOf("")

    fun createTicket() {
        val ticket = Ticket(routeNumber.value, licensePlate.value)
        createTicketUseCase.execute(ticket)
    }
}
