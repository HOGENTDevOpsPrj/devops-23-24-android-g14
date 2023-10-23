package com.hogent.svkapp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hogent.svkapp.data.repositories.TicketRepository
import com.hogent.svkapp.domain.Validator
import com.hogent.svkapp.domain.entities.CreationResult
import com.hogent.svkapp.domain.entities.ErrorType
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.domain.entities.Ticket
import java.util.Locale

class CreateTicketScreenViewModel(
    private val validator: Validator, private val ticketRepository: TicketRepository,
) : ViewModel() {
    private var _routeNumber = mutableStateOf(value = "")
    val routeNumber: State<String> get() = _routeNumber

    private var _licensePlate = mutableStateOf(value = "")
    val licensePlate: State<String> get() = _licensePlate

    private val _images = mutableStateListOf<Image>()
    val images: List<Image> get() = _images

    private var _routeNumberError = mutableStateOf<String?>(value = null)
    val routeNumberError: State<String?> get() = _routeNumberError

    private var _licensePlateError = mutableStateOf<String?>(value = null)
    val licensePlateError: State<String?> get() = _licensePlateError

    private var _imagesError = mutableStateOf<String?>(value = null)
    // val imagesError: State<String?> get() = _imagesError

    fun addImage(image: Image) {
        _images.add(element = image)
    }

    fun deleteImage(image: Image) {
        _images.remove(image)
    }

    fun onSend() {
        val creationResult =
            Ticket.create(validator, _routeNumber.value, _licensePlate.value, _images)

        when (creationResult) {
            is CreationResult.Success -> {
                ticketRepository.addTicket(creationResult.ticket)
                resetForm()
            }

            is CreationResult.Failure -> {
                _routeNumberError.value = creationResult.errors.find {
                    it in listOf(
                        ErrorType.EMPTY_ROUTE, ErrorType.INVALID_ROUTE_NUMBER
                    )
                }?.message
                _licensePlateError.value = creationResult.errors.find {
                    it in listOf(
                        ErrorType.EMPTY_LICENSE_PLATE, ErrorType.LONG_LICENSE_PLATE
                    )
                }?.message
                _imagesError.value =
                    creationResult.errors.find { it == ErrorType.EMPTY_IMAGES }?.message
            }
        }
    }

    fun onRouteNumberChange(routeNumber: String) {
        _routeNumber.value = routeNumber
        validate(ValidationType.ROUTE_NUMBER)
    }

    fun onLicensePlateChange(licensePlate: String) {
        _licensePlate.value = licensePlate.uppercase(locale = Locale.ROOT)
        validate(ValidationType.LICENSE_PLATE)
    }

    private fun validate(type: ValidationType) {
        val result = when (type) {
            ValidationType.ROUTE_NUMBER -> validator.validateRouteNumber(routeNumber.value)
            ValidationType.LICENSE_PLATE -> validator.validateLicensePlate(licensePlate.value)
            ValidationType.IMAGES -> validator.validateImages(images)
        }

        when (type) {
            ValidationType.ROUTE_NUMBER -> _routeNumberError.value = result?.message
            ValidationType.LICENSE_PLATE -> _licensePlateError.value = result?.message
            ValidationType.IMAGES -> _imagesError.value = result?.message
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

private enum class ValidationType {
    ROUTE_NUMBER, LICENSE_PLATE, IMAGES
}
