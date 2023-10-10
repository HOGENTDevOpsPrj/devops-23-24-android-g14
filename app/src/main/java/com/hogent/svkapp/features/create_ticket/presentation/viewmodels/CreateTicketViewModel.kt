package com.hogent.svkapp.features.create_ticket.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hogent.svkapp.features.create_ticket.domain.TicketCreator
import com.hogent.svkapp.features.create_ticket.domain.Validator
import com.hogent.svkapp.features.create_ticket.domain.entities.ValidationResult

class CreateTicketViewModel(
    private val validator: Validator, private val ticketCreator: TicketCreator,
) : ViewModel() {
    val routeNumber = mutableStateOf("")
    val licensePlate = mutableStateOf("")
    val routeNumberError = mutableStateOf<String?>(null)
    val licensePlateError = mutableStateOf<String?>(null)

    fun onSend() {
        validateRouteNumber()
        validateLicensePlate()

        if (routeNumberError.value == null && licensePlateError.value == null) {
            ticketCreator.createTicket(
                routeNumber = validator.sanitizeRouteNumber(routeNumber.value),
                licensePlate = validator.sanitizeLicensePlate(licensePlate.value),
            )

            resetForm()
        }
    }

    fun onRouteNumberChange(routeNumber: String) {
        this.routeNumber.value = routeNumber
        validateRouteNumber()
    }

    fun onLicensePlateChange(licensePlate: String) {
        this.licensePlate.value = licensePlate
        validateLicensePlate()
    }

    private fun validateRouteNumber() {
        val validationResult = validator.validateRouteNumber(routeNumber.value)
        routeNumberError.value = when (validationResult) {
            is ValidationResult.Valid -> null
            is ValidationResult.Invalid -> validationResult.message
        }
    }

    private fun validateLicensePlate() {
        val validationResult = validator.validateLicensePlate(licensePlate.value)
        licensePlateError.value = when (validationResult) {
            is ValidationResult.Valid -> null
            is ValidationResult.Invalid -> validationResult.message
        }
    }

    private fun resetForm() {
        routeNumber.value = ""
        licensePlate.value = ""
        routeNumberError.value = null
        licensePlateError.value = null
    }
}
