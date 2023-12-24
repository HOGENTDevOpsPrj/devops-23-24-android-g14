package com.hogent.svkapp.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.hogent.svkapp.R
import com.hogent.svkapp.SVKApplication
import com.hogent.svkapp.data.repositories.MainCargoTicketRepository
import com.hogent.svkapp.data.repositories.UserApiRepository
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.domain.entities.ImageCollectionError
import com.hogent.svkapp.domain.entities.LicensePlate
import com.hogent.svkapp.domain.entities.LoadReceiptNumber
import com.hogent.svkapp.domain.entities.RouteNumber
import com.hogent.svkapp.domain.entities.RouteNumberCollectionError
import com.hogent.svkapp.domain.entities.User
import com.hogent.svkapp.util.CustomResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Locale

/**
 * The [ViewModel] of the main screen.
 *
 * @param mainCargoTicketRepository the [MainCargoTicketRepository] that is used to add cargo tickets.
 * @param userApiRepository the [UserApiRepository] that is used to add users.
 * @property userIsAuthenticated whether the user is authenticated.
 * @property user the user.
 * @property uiState the state of the screen as read-only state flow.
 */
class MainScreenViewModel(
    private val mainCargoTicketRepository: MainCargoTicketRepository, private val userApiRepository: UserApiRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenState())

    /**
     * The state of the screen as read-only state flow.
     */
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    private val tag = "MainScreenViewModel"

    var userIsAuthenticated: Boolean by mutableStateOf(false)
    var user: User by mutableStateOf(User())

    /**
     * Called when login button is clicked.
     */
    fun onLogin(context: Context, auth: Auth0, onSuccessNavigation: () -> Unit) {
        WebAuthProvider
            .login(auth)
            .withScheme(context.getString(R.string.com_auth0_scheme))
            .withAudience(context.getString(R.string.com_auth0_audience))
            .start(
                context,
                object : Callback<Credentials, AuthenticationException> {
                    override fun onFailure(error: AuthenticationException) {
                    }

                    override fun onSuccess(result: Credentials) {
                        val idToken = result.accessToken
                        user = User(idToken)
                        Log.d(tag, "User: ${user.id}")
                        userIsAuthenticated = true

                        user.idToken?.let { saveTokenToSharedPreferences(context, it) }

                        viewModelScope.launch {
                            userApiRepository.addUser(user)
                        }
                        onSuccessNavigation()
                    }
                },
            )
    }

    internal fun saveTokenToSharedPreferences(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("AuthPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("auth0_token", token).apply()
    }

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
                val cargoTicketRepository = application.container.mainCargoTicketRepository
                val userRepository = application.container.userApiRepository
                MainScreenViewModel(
                    mainCargoTicketRepository = cargoTicketRepository, userApiRepository = userRepository
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
            loadReceiptNumber = _uiState.value.loadReceiptNumberInputFieldValue,
            routeNumbers = _uiState.value.routeNumberInputFieldValues,
            licensePlate = _uiState.value.licensePlateInputFieldValue,
            images = _uiState.value.imageCollection,
            freightLoaderId = user.id,
            registrationDateTime = LocalDateTime.now()
        )

        when (creationResult) {
            is CustomResult.Success -> {
                viewModelScope.launch { mainCargoTicketRepository.addCargoTicket(creationResult.value) }
                resetForm()
                toggleDialog()
            }

            is CustomResult.Failure -> {
                _uiState.update { state ->
                    state.copy(
                        loadReceiptNumberInputFieldValidationError = creationResult.error.loadReceiptNumberError,
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
            state.copy(routeNumberInputFieldValues = state.routeNumberInputFieldValues.toMutableList().apply {
                set(index, routeNumber)
            },
                routeNumberInputFieldValidationErrors = state.routeNumberInputFieldValidationErrors.toMutableList()
                    .apply {
                        set(index, RouteNumber.validateStringRepresentation(routeNumber))
                    })
        }
    }

    /**
     * Add a route number to the route number collection.
     */
    fun addRouteNumber() {
        _uiState.update { state ->
            state.copy(
                routeNumberInputFieldValues = state.routeNumberInputFieldValues.toMutableList().apply {
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
                routeNumberCollectionError = if (state.routeNumberInputFieldValues.isEmpty()) RouteNumberCollectionError.EMPTY else null
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
     * Updates the value of the load receipt number input field. If the value doesn't represent a
     * valid load receipt number, the corresponding validation error is set.
     *
     * @param loadReceiptNumber the new value of the load receipt number input field.
     */
    fun onLoadReceiptNumberChange(loadReceiptNumber: String) {
        _uiState.update { state ->
            state.copy(
                loadReceiptNumberInputFieldValue = loadReceiptNumber,
                loadReceiptNumberInputFieldValidationError = LoadReceiptNumber.validate(loadReceiptNumber)
            )
        }
    }

    /**
     * Navigates to the login screen.
     */
    fun onLogout(context: Context, auth: Auth0, onLogoutNavigation: () -> Unit) {
        WebAuthProvider.logout(auth).withScheme(context.getString(R.string.com_auth0_scheme))
            .start(context, object : Callback<Void?, AuthenticationException> {

                override fun onFailure(error: AuthenticationException) {
                    // For some reason, logout failed.
                    Log.e(tag, "Error occurred in logout(): $error")
                }

                override fun onSuccess(result: Void?) {
                    // The user successfully logged out.
                    userIsAuthenticated = false
                    user = User()
                    onLogoutNavigation()
                }

            })
    }

    private fun validateImageCollection() {
        _uiState.update { state ->
            state.copy(
                imageCollectionError = if (state.imageCollection.isEmpty()) ImageCollectionError.EMPTY else null
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
