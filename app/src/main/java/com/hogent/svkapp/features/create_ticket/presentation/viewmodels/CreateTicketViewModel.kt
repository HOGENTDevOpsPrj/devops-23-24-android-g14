package com.hogent.svkapp.features.create_ticket.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hogent.svkapp.features.create_ticket.data.repositories.TicketRepository
import com.hogent.svkapp.features.create_ticket.domain.Validator
import com.hogent.svkapp.features.create_ticket.domain.entities.Ticket
import com.hogent.svkapp.features.create_ticket.domain.entities.ValidationResult

class CreateTicketViewModel(
    private val validator: Validator, private val ticketRepository: TicketRepository
) : ViewModel() {
    val routeNumber = mutableStateOf("")
    val licensePlate = mutableStateOf("")
    val routeNumberError = mutableStateOf<String?>(null)
    val licensePlateError = mutableStateOf<String?>(null)

    fun createTicket() {
        val routeNumber = routeNumber.value
        val licensePlate = licensePlate.value
        var hasError = false

        val routeNumberValidationResult = validator.validateRouteNumber(routeNumber = routeNumber)

        if (routeNumberValidationResult is ValidationResult.Invalid) {
            routeNumberError.value = routeNumberValidationResult.message
            hasError = true
        } else {
            routeNumberError.value = null
        }

        val licensePlateValidationResult =
            validator.validateLicensePlate(licensePlate = licensePlate)

        if (licensePlateValidationResult is ValidationResult.Invalid) {
            licensePlateError.value = licensePlateValidationResult.message
            hasError = true
        } else {
            licensePlateError.value = null
        }

        if (!hasError) {
            val ticket = Ticket(routeNumber = routeNumber, licensePlate = licensePlate)
            ticketRepository.addTicket(ticket = ticket)
        }
    }
}
