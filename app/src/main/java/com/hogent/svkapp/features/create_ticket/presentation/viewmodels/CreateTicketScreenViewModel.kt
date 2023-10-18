package com.hogent.svkapp.features.create_ticket.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hogent.svkapp.features.create_ticket.domain.TicketCreator
import com.hogent.svkapp.features.create_ticket.domain.Validator
import com.hogent.svkapp.features.create_ticket.domain.entities.ValidationResult
import com.hogent.svkapp.features.upload_image.domain.Image

class CreateTicketScreenViewModel(
    private val validator: Validator, private val ticketCreator: TicketCreator,
) : ViewModel() {
    private var _routeNumber = mutableStateOf("")
    private var _licensePlate = mutableStateOf("")
    private var _routeNumberError = mutableStateOf<String?>(null)
    private var _licensePlateError = mutableStateOf<String?>(null)
    private val _images = mutableStateListOf<Image>()

    val routeNumber: State<String> get() = _routeNumber
    val licensePlate: State<String> get() = _licensePlate
    val routeNumberError: State<String?> get() = _routeNumberError
    val licensePlateError: State<String?> get() = _licensePlateError
    val images get() = _images

    fun addImage(image: Image) {
        _images.add(image)
    }

    fun deleteImage(image: Image) {
        _images.remove(image)
    }

    fun onSend() {
        validateRouteNumber()
        validateLicensePlate()

        if (routeNumberError.value == null && licensePlateError.value == null) {
            ticketCreator.createTicket(
                routeNumber = validator.sanitizeRouteNumber(routeNumber.value),
                licensePlate = validator.sanitizeLicensePlate(licensePlate.value),
                images = images
            )

            resetForm()
        }
    }

    fun onRouteNumberChange(routeNumber: String) {
        _routeNumber.value = routeNumber
        validateRouteNumber()
    }

    fun onLicensePlateChange(licensePlate: String) {
        _licensePlate.value = licensePlate
        validateLicensePlate()
    }

    private fun validateRouteNumber() {
        val validationResult = validator.validateRouteNumber(routeNumber.value)
        _routeNumberError.value = when (validationResult) {
            is ValidationResult.Valid -> null
            is ValidationResult.Invalid -> validationResult.message
        }
    }

    private fun validateLicensePlate() {
        val validationResult = validator.validateLicensePlate(licensePlate.value)
        _licensePlateError.value = when (validationResult) {
            is ValidationResult.Valid -> null
            is ValidationResult.Invalid -> validationResult.message
        }
    }

    private fun resetForm() {
        _routeNumber.value = ""
        _licensePlate.value = ""
        _routeNumberError.value = null
        _licensePlateError.value = null
        _images.clear()
    }
}
