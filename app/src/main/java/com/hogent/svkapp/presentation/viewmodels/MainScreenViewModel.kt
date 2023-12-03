package com.hogent.svkapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hogent.svkapp.SVKApplication
import com.hogent.svkapp.data.repositories.CargoTicketRepository
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.domain.entities.ImageCollectionError
import com.hogent.svkapp.domain.entities.LicensePlate
import com.hogent.svkapp.domain.entities.Result
import com.hogent.svkapp.domain.entities.RouteNumber
import com.hogent.svkapp.domain.entities.RouteNumberCollectionError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * The [ViewModel] of the main screen.
 *
 * @param cargoTicketRepository the [CargoTicketRepository] that is used to add cargo tickets.
 *
 */
class MainScreenViewModel(
    private val cargoTicketRepository: CargoTicketRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenState())

    /**
     * The state of the screen as read-only state flow.
     */
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    /**
     * Toggles the dialog.
     */
    fun toggleDialog() {
        _uiState.update { state ->
            state.copy(showPopup = !state.showPopup)
        }
    }

    companion object {
        /**
         * The [ViewModelProvider.Factory] of the [MainScreenViewModel].
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SVKApplication)
                val cargoTicketRepository = application.container.cargoTicketRepository
                MainScreenViewModel(
                    cargoTicketRepository = cargoTicketRepository,
                )
            }
        }
    }

    /**
     * Adds an image to the image collection.
     *
     * @param image the image to add.
     */
    fun addImage(image: Image) {
        _uiState.update { state ->
            state.copy(imageCollection = state.imageCollection + image)
        }
        validateImageCollection()
    }

    /**
     * Removes an image from the image collection.
     *
     * @param image the image to remove.
     */
    fun removeImage(image: Image) {
        _uiState.update { state ->
            state.copy(imageCollection = state.imageCollection - image)
        }
        validateImageCollection()
    }

    /**
     * Creates a cargo ticket and adds it to the repository. If the creation fails, the validation
     * errors are shown. If the creation succeeds, the form is reset and the dialog is shown.
     */
    fun onSend() {
        val creationResult = CargoTicket.create(
            routeNumbers = _uiState.value.routeNumberInputFieldValues,
            licensePlate = _uiState.value.licensePlateInputFieldValue,
            images = _uiState.value.imageCollection
        )

        when (creationResult) {
            is Result.Success -> {
                viewModelScope.launch { cargoTicketRepository.addCargoTicket(creationResult.value) }
                resetForm()
                toggleDialog()
            }

            is Result.Failure -> {
                _uiState.update { state ->
                    state.copy(
                        routeNumberCollectionError = creationResult.error.routeNumberCollectionError,
                        routeNumberInputFieldValidationErrors = creationResult.error.routeNumberErrors,
                        licensePlateInputFieldValidationError = creationResult.error.licensePlateError,
                        imageCollectionError = creationResult.error.imageCollectionError
                    )
                }
            }
        }
    }

    /**
     * Updates the value of a route number input field. If the value doesn't represent a valid
     * route number, the corresponding validation error is set.
     *
     * @param index the index of the route number input field.
     * @param routeNumber the new value of the route number input field.
     */
    fun onRouteNumberChange(index: Int, routeNumber: String) {
        _uiState.update { state ->
            state.copy(
                routeNumberInputFieldValues = state.routeNumberInputFieldValues.toMutableList().apply {
                    set(index, routeNumber)
                },
                routeNumberInputFieldValidationErrors = state.routeNumberInputFieldValidationErrors.toMutableList()
                    .apply {
                        set(index, RouteNumber.validateStringRepresentation(routeNumber))
                    }
            )
        }
    }

    /**
     * Add a route number to the route number collection.
     */
    fun addRouteNumber() {
        _uiState.update { state ->
            state.copy(
                routeNumberInputFieldValues = state.routeNumberInputFieldValues.toMutableList()
                    .apply {
                        add("")
                    },
                routeNumberInputFieldValidationErrors = state.routeNumberInputFieldValidationErrors.toMutableList()
                    .apply {
                        add(emptyList())
                    },
                routeNumberCollectionError = null
            )
        }
    }

    /**
     * Remove a route number from the route number collection.
     *
     * @param index the index of the route number to remove.
     */
    fun removeRouteNumber(index: Int) {
        _uiState.update { state ->
            state.copy(
                routeNumberInputFieldValues = state.routeNumberInputFieldValues.toMutableList().apply {
                    removeAt(index)
                },
                routeNumberInputFieldValidationErrors = state.routeNumberInputFieldValidationErrors.toMutableList()
                    .apply {
                        removeAt(index)
                    },
                routeNumberCollectionError = if (state.routeNumberInputFieldValues.isEmpty()) RouteNumberCollectionError.Empty else null
            )
        }
    }

    /**
     * Updates the value of the license plate input field. If the value doesn't represent a valid
     * license plate, the corresponding validation error is set.
     *
     * @param licensePlate the new value of the license plate input field.
     */
    fun onLicensePlateChange(licensePlate: String) {
        _uiState.update { state ->
            state.copy(
                licensePlateInputFieldValue = licensePlate.uppercase(locale = Locale.ROOT),
                licensePlateInputFieldValidationError = LicensePlate.validate(licensePlate)
            )
        }
    }

    /**
     * Navigates to the login screen.
     */
    fun onLogout() {
        // Loguit
    }

    private fun validateImageCollection() {
        _uiState.update { state ->
            state.copy(
                imageCollectionError = if (state.imageCollection.isEmpty()) ImageCollectionError.Empty else null
            )
        }
    }

    private fun resetForm() {
        _uiState.update { state ->
            state.copy(
                routeNumberInputFieldValues = listOf(""),
                licensePlateInputFieldValue = "",
                imageCollection = emptyList(),
                routeNumberInputFieldValidationErrors = listOf(emptyList()),
                routeNumberCollectionError = null,
                licensePlateInputFieldValidationError = null,
                imageCollectionError = null
            )
        }
    }
}
