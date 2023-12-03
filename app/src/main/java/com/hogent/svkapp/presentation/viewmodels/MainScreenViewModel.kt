package com.hogent.svkapp.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.callback.Callback
import com.hogent.svkapp.R
import com.hogent.svkapp.Route
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
import java.util.Locale

/**
 * The [ViewModel] of the main screen.
 *
 * @param cargoTicketRepository the [CargoTicketRepository] that is used to add cargo tickets.
 * @param navController the [NavHostController] that is used to navigate to other screens.
 *
 */
class MainScreenViewModel(
    private val cargoTicketRepository: CargoTicketRepository = CargoTicketRepository(),
    private val navController: NavHostController
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenState())

    /**
     * The state of the screen as read-only state flow.
     */
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    private val TAG = "MainScreenViewModel"

    /**
     * Toggles the dialog.
     */
    fun toggleDialog() {
        _uiState.update { state ->
            state.copy(showPopup = !state.showPopup)
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
        Log.d("State on send", uiState.value.toString())

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
        Log.d("State on add", uiState.value.toString())
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
        Log.d("State on remove", uiState.value.toString())
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
    fun onLogout(context: Context, auth: Auth0, onLogoutNavigation: () -> Unit) {
        WebAuthProvider
            .logout(auth)
            .withScheme(context.getString(R.string.com_auth0_scheme))
            .start(context, object : Callback<Void?, AuthenticationException> {

                override fun onFailure(error: AuthenticationException) {
                    // For some reason, logout failed.
                    Log.e(TAG, "Error occurred in logout(): $error")
                }

                override fun onSuccess(result: Void?) {
                    // The user successfully logged out.
                    // userIsAuthenticated = false
                    onLogoutNavigation
                }

            })
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
