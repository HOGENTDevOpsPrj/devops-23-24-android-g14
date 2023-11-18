package com.hogent.svkapp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.hogent.svkapp.Route
import com.hogent.svkapp.data.repositories.CargoTicketRepository
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.domain.entities.ImageCollectionError
import com.hogent.svkapp.domain.entities.LicensePlate
import com.hogent.svkapp.domain.entities.LicensePlateError
import com.hogent.svkapp.domain.entities.Result
import com.hogent.svkapp.domain.entities.RouteNumber
import com.hogent.svkapp.domain.entities.RouteNumberCollectionError
import com.hogent.svkapp.domain.entities.RouteNumberError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

/**
 * The [ViewModel] of the main screen.
 *
 * @param cargoTicketRepository the [CargoTicketRepository] that is used to add cargo tickets.
 * @param navController the [NavHostController] that is used to navigate to other screens.
 *
 * @property routeNumberInputFieldValues the values of the route number input fields.
 * @property licensePlateInputFieldValue the value of the license plate input field.
 * @property imageCollection the images that are added to the cargo ticket.
 * @property routeNumberInputFieldValidationErrors the validation errors of the route number input fields.
 * @property routeNumberCollectionError the validation error of the route number collection.
 * @property licensePlateInputFieldValidationError the validation error of the license plate input field.
 * @property imageCollectionError the validation error of the image collection.
 * @property showDialog whether or not the dialog should be shown.
 */
class MainScreenViewModel(
    private val cargoTicketRepository: CargoTicketRepository = CargoTicketRepository(),
    private val navController: NavHostController
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    /**
     * Toggles the dialog.
     */
    fun toggleDialog() {
        //uiState.showDialog.value = !showDialog.value

        _uiState.update { state ->
            state.copy(showDialog = !state.showDialog)
        }
    }

    /**
     * Adds an image to the image collection.
     *
     * @param image the image to add.
     */
    fun addImage(image: Image) {
//        _imageCollection.add(image)
//        validateImageCollection()

        _uiState.update { state ->
            state.copy(imageCollection = state.imageCollection + image)
        }
    }

    /**
     * Removes an image from the image collection.
     *
     * @param image the image to remove.
     */
    fun removeImage(image: Image) {
//        _imageCollection.remove(image)
//        validateImageCollection()

        _uiState.update { state ->
            state.copy(imageCollection = state.imageCollection - image)
        }
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
                cargoTicketRepository.addCargoTicket(creationResult.value)
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
                    ) }
//                _routeNumberCollectionError.value = creationResult.error.routeNumberCollectionError
//                _routeNumberInputFieldValidationErrors.clear()
//                _routeNumberInputFieldValidationErrors.addAll(creationResult.error.routeNumberErrors)
//                _licensePlateInputFieldValidationError.value =
//                    creationResult.error.licensePlateError
//                _imageCollectionError.value = creationResult.error.imageCollectionError
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
//        _routeNumberInputFieldValues[index] = routeNumber
//
//        _routeNumberInputFieldValidationErrors[index] =
//            RouteNumber.validateStringRepresentation(routeNumber)
//
//        if (routeNumber.isEmpty()) {
//            _routeNumberInputFieldValidationErrors[index] = null
//        }

        _uiState.update { state ->
            state.copy(
                routeNumberInputFieldValues = state.routeNumberInputFieldValues.toMutableList().apply {
                    set(index, routeNumber)
                },
                routeNumberInputFieldValidationErrors = state.routeNumberInputFieldValidationErrors.toMutableList().apply {
                    set(index, RouteNumber.validateStringRepresentation(routeNumber))
                }
            )
        }
    }

    /**
     * Add a route number to the route number collection.
     */
    fun addRouteNumber() {
//        _routeNumberInputFieldValues.add("")
//        _routeNumberInputFieldValidationErrors.add(null)
//        _routeNumberCollectionError.value = null

        _uiState.update { state ->
            state.copy(
                routeNumberInputFieldValues = state.routeNumberInputFieldValues + "",
                routeNumberInputFieldValidationErrors = state.routeNumberInputFieldValidationErrors + null,
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
//        _routeNumberInputFieldValues.removeAt(index)
//        _routeNumberInputFieldValidationErrors.removeAt(index)
//        if (_routeNumberInputFieldValues.isEmpty()) {
//            _routeNumberCollectionError.value = RouteNumberCollectionError.Empty
//        }

        _uiState.update { state ->
            state.copy(
                routeNumberInputFieldValues = state.routeNumberInputFieldValues.toMutableList().apply {
                    removeAt(index)
                },
                routeNumberInputFieldValidationErrors = state.routeNumberInputFieldValidationErrors.toMutableList().apply {
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
//        _licensePlateInputFieldValue.value = licensePlate.uppercase(locale = Locale.ROOT)
//
//        _licensePlateInputFieldValidationError.value = LicensePlate.validate(licensePlate)
//
//        if (_licensePlateInputFieldValue.value.isEmpty()) {
//            _licensePlateInputFieldValidationError.value = null
//        }

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
        navController.navigate(route = Route.Login.name)
    }

    private fun validateImageCollection() {
//        if (_imageCollection.isEmpty()) _imageCollectionError.value = ImageCollectionError.Empty
//        else _imageCollectionError.value = null

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
                imageCollection = listOf(),
                routeNumberInputFieldValidationErrors = listOf(null),
                routeNumberCollectionError = null,
                licensePlateInputFieldValidationError = null,
                imageCollectionError = null
            )
        }
//        _routeNumberInputFieldValues.clear()
//        _routeNumberInputFieldValues.add("")
//        _licensePlateInputFieldValue.value = ""
//        _imageCollection.clear()
//        _routeNumberInputFieldValidationErrors.clear()
//        _routeNumberInputFieldValidationErrors.add(null)
//        _routeNumberCollectionError.value = null
//        _licensePlateInputFieldValidationError.value = null
//        _imageCollectionError.value = null
    }
}
