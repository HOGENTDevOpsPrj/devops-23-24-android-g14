package com.hogent.svkapp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.hogent.svkapp.Route
import com.hogent.svkapp.data.repositories.CargoTicketRepository
import com.hogent.svkapp.domain.ValidationError
import com.hogent.svkapp.domain.ValidationResult
import com.hogent.svkapp.domain.Validator
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.CreationResult
import com.hogent.svkapp.domain.entities.Image
import java.util.Locale

class MainScreenViewModel(
    private val validator: Validator = Validator(),
    private val cargoTicketRepository: CargoTicketRepository = CargoTicketRepository(),
    private val navController: NavHostController
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
    val imagesError: State<String?> get() = _imagesError

    private var _showDialog = mutableStateOf(value = false)
    val showDialog: State<Boolean> get() = _showDialog

    fun toggleDialog() {
        _showDialog.value = !_showDialog.value
    }

    fun addImage(image: Image) {
        _images.add(element = image)
        validate(ValidationType.IMAGES)
    }

    fun deleteImage(image: Image) {
        _images.remove(image)
        validate(ValidationType.IMAGES)
    }

    fun onSend() {
        val creationResult = CargoTicket.create(
            routeNumber = _routeNumber.value,
            licensePlate = _licensePlate.value,
            images = _images,
            validator = validator
        )

        when (creationResult) {
            is CreationResult.Success -> handleSuccess(creationResult)
            is CreationResult.Failure -> handleFailure(creationResult)
        }
    }

    private fun handleSuccess(result: CreationResult.Success) {
        cargoTicketRepository.addCargoTicket(result.cargoTicket)
        resetForm()
        toggleDialog()
    }

    private fun handleFailure(result: CreationResult.Failure) {
        result.errors.forEach { error ->
            when (error) {
                ValidationError.EMPTY_ROUTE -> _routeNumberError.value = error.message
                ValidationError.INVALID_ROUTE_NUMBER -> _routeNumberError.value = error.message
                ValidationError.EMPTY_LICENSE_PLATE -> _licensePlateError.value = error.message
                ValidationError.LONG_LICENSE_PLATE -> _licensePlateError.value = error.message
                ValidationError.EMPTY_IMAGES -> _imagesError.value = error.message
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

    fun onLogout() {
        navController.navigate(route = Route.Login.name)
    }

    private fun validate(type: ValidationType) {
        val result = when (type) {
            ValidationType.ROUTE_NUMBER -> validator.validateRouteNumber(routeNumber.value)
            ValidationType.LICENSE_PLATE -> validator.validateLicensePlate(licensePlate.value)
            ValidationType.IMAGES -> validator.validateImages(images)
        }

        when (result) {
            is ValidationResult.Success -> {
                when (type) {
                    ValidationType.ROUTE_NUMBER -> _routeNumberError.value = null
                    ValidationType.LICENSE_PLATE -> _licensePlateError.value = null
                    ValidationType.IMAGES -> _imagesError.value = null
                }
            }

            is ValidationResult.Error -> {
                when (type) {
                    ValidationType.ROUTE_NUMBER -> _routeNumberError.value = result.error.message
                    ValidationType.LICENSE_PLATE -> _licensePlateError.value = result.error.message
                    ValidationType.IMAGES -> _imagesError.value = result.error.message
                }
            }
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
