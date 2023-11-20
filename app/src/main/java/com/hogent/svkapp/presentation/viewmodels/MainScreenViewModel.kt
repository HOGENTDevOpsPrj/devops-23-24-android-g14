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
    private var _routeNumberInputFieldValues = mutableStateListOf("")
    val routeNumberInputFieldValues: SnapshotStateList<String> get() = _routeNumberInputFieldValues
    private var _licensePlateInputFieldValue = mutableStateOf(value = "")
    val licensePlateInputFieldValue: State<String> get() = _licensePlateInputFieldValue
    private val _imageCollection = mutableStateListOf<Image>()
    val imageCollection: SnapshotStateList<Image> get() = _imageCollection
    private var _routeNumberInputFieldValidationErrors = mutableStateListOf<RouteNumberError?>(null)
    val routeNumberInputFieldValidationErrors: List<RouteNumberError?> get() = _routeNumberInputFieldValidationErrors
    private var _routeNumberCollectionError =
        mutableStateOf<RouteNumberCollectionError?>(value = null)
    val routeNumberCollectionError: State<RouteNumberCollectionError?> get() = _routeNumberCollectionError
    private var _licensePlateInputFieldValidationError =
        mutableStateOf<LicensePlateError?>(value = null)
    val licensePlateInputFieldValidationError: State<LicensePlateError?> get() = _licensePlateInputFieldValidationError
    private var _imageCollectionError = mutableStateOf<ImageCollectionError?>(value = null)
    val imageCollectionError: State<ImageCollectionError?> get() = _imageCollectionError
    private var _showDialog = mutableStateOf(value = false)
    val showDialog: State<Boolean> get() = _showDialog

    /**
     * Toggles the dialog.
     */
    fun toggleDialog() {
        _showDialog.value = !_showDialog.value
    }

    /**
     * Adds an image to the image collection.
     *
     * @param image the image to add.
     */
    fun addImage(image: Image) {
        _imageCollection.add(image)
        validateImageCollection()
    }

    /**
     * Removes an image from the image collection.
     *
     * @param image the image to remove.
     */
    fun removeImage(image: Image) {
        _imageCollection.remove(image)
        validateImageCollection()
    }

    /**
     * Creates a cargo ticket and adds it to the repository. If the creation fails, the validation
     * errors are shown. If the creation succeeds, the form is reset and the dialog is shown.
     */
    fun onSend() {
        val creationResult = CargoTicket.create(
            routeNumbers = _routeNumberInputFieldValues,
            licensePlate = _licensePlateInputFieldValue.value,
            images = _imageCollection
        )

        when (creationResult) {
            is Result.Success -> {
                cargoTicketRepository.addCargoTicket(creationResult.value)
                resetForm()
                toggleDialog()
            }

            is Result.Failure -> {
                _routeNumberCollectionError.value = creationResult.error.routeNumberCollectionError
                _routeNumberInputFieldValidationErrors.clear()
                _routeNumberInputFieldValidationErrors.addAll(creationResult.error.routeNumberErrors)
                _licensePlateInputFieldValidationError.value =
                    creationResult.error.licensePlateError
                _imageCollectionError.value = creationResult.error.imageCollectionError
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
        _routeNumberInputFieldValues[index] = routeNumber

        _routeNumberInputFieldValidationErrors[index] =
            RouteNumber.validateStringRepresentation(routeNumber)

        if (routeNumber.isEmpty()) {
            _routeNumberInputFieldValidationErrors[index] = null
        }
    }

    /**
     * Add a route number to the route number collection.
     */
    fun addRouteNumber() {
        _routeNumberInputFieldValues.add("")
        _routeNumberInputFieldValidationErrors.add(null)
        _routeNumberCollectionError.value = null
    }

    /**
     * Remove a route number from the route number collection.
     *
     * @param index the index of the route number to remove.
     */
    fun removeRouteNumber(index: Int) {
        _routeNumberInputFieldValues.removeAt(index)
        _routeNumberInputFieldValidationErrors.removeAt(index)
        if (_routeNumberInputFieldValues.isEmpty()) {
            _routeNumberCollectionError.value = RouteNumberCollectionError.Empty
        }
    }

    /**
     * Updates the value of the license plate input field. If the value doesn't represent a valid
     * license plate, the corresponding validation error is set.
     *
     * @param licensePlate the new value of the license plate input field.
     */
    fun onLicensePlateChange(licensePlate: String) {
        _licensePlateInputFieldValue.value = licensePlate.uppercase(locale = Locale.ROOT)

        _licensePlateInputFieldValidationError.value = LicensePlate.validate(licensePlate)

        if (_licensePlateInputFieldValue.value.isEmpty()) {
            _licensePlateInputFieldValidationError.value = null
        }
    }

    /**
     * Navigates to the login screen.
     */
    fun onLogout() {
        navController.navigate(route = Route.Login.name)
    }

    fun onTakePhoto() {
        navController.navigate(route = Route.Camera.name)
    }

    private fun validateImageCollection() {
        if (_imageCollection.isEmpty()) _imageCollectionError.value = ImageCollectionError.Empty
        else _imageCollectionError.value = null
    }

    private fun resetForm() {
        _routeNumberInputFieldValues.clear()
        _routeNumberInputFieldValues.add("")
        _licensePlateInputFieldValue.value = ""
        _imageCollection.clear()
        _routeNumberInputFieldValidationErrors.clear()
        _routeNumberInputFieldValidationErrors.add(null)
        _routeNumberCollectionError.value = null
        _licensePlateInputFieldValidationError.value = null
        _imageCollectionError.value = null
    }
}
