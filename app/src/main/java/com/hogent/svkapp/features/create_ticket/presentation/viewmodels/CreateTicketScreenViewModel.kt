package com.hogent.svkapp.features.create_ticket.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hogent.svkapp.features.create_ticket.domain.TicketCreator
import com.hogent.svkapp.features.create_ticket.domain.Validator
import com.hogent.svkapp.features.create_ticket.domain.entities.ValidationResult
import com.hogent.svkapp.features.upload_image.domain.Image
import java.util.Locale

class CreateTicketScreenViewModel(
    private val validator: Validator, private val ticketCreator: TicketCreator,
) : ViewModel() {
    private var _routeNumber = mutableStateOf("")
    private var _licensePlate = mutableStateOf("")
    private val _images = mutableStateListOf<Image>()

    private var _routeNumberError = mutableStateOf<String?>(null)
    private var _licensePlateError = mutableStateOf<String?>(null)
    private var _imagesError = mutableStateOf<String?>(null)

    val routeNumber: State<String> get() = _routeNumber
    val licensePlate: State<String> get() = _licensePlate
    val images get() = _images

    val routeNumberError: State<String?> get() = _routeNumberError
    val licensePlateError: State<String?> get() = _licensePlateError
    val imagesError: State<String?> get() = _imagesError

    fun addImage(image: Image) {
        _images.add(image)
        validateImages()
    }

    fun onSend() {
        validateRouteNumber()
        validateLicensePlate()
        validateImages()

        if (routeNumberError.value == null && licensePlateError.value == null && _imagesError.value == null) {
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
        _licensePlate.value = licensePlate.uppercase(Locale.ROOT)
        validateLicensePlate()
    }

    private fun validateRouteNumber() {
        val validationResult = validator.validateRouteNumber(routeNumber = routeNumber.value)
        _routeNumberError.value = when (validationResult) {
            is ValidationResult.Valid -> null
            is ValidationResult.Invalid -> validationResult.message
        }
    }

    private fun validateLicensePlate() {
        val validationResult = validator.validateLicensePlate(licensePlate = licensePlate.value)
        _licensePlateError.value = when (validationResult) {
            is ValidationResult.Valid -> null
            is ValidationResult.Invalid -> validationResult.message
        }
    }

    private fun validateImages() {
        val validationResult = validator.validateImages(photos = images)
        _imagesError.value = when (validationResult) {
            is ValidationResult.Valid -> null
            is ValidationResult.Invalid -> validationResult.message
        }
    }

    private fun resetForm() {
        _routeNumber.value = ""
        _licensePlate.value = ""
        _images.clear()

        _routeNumberError.value = null
        _licensePlateError.value = null
        _imagesError.value = null
    }
}
