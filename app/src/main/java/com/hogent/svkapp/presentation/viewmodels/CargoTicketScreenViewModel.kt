package com.hogent.svkapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hogent.svkapp.SVKApplication
import com.hogent.svkapp.data.repositories.MainCargoTicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * The [ViewModel] of the CargoTicketScreen.
 *
 * @property cargoTicketRepository the [MainCargoTicketRepository] that is used to get the CargoTickets.
 */
class CargoTicketScreenViewModel(
    private val cargoTicketRepository: MainCargoTicketRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CargoTicketScreenState())

    /**
     * The state of the screen as read-only state flow.
     */
    val uiState: StateFlow<CargoTicketScreenState> = _uiState.asStateFlow()

    init {
        getCargoTickets()
    }

    companion object {
        /**
         * The [ViewModelProvider.Factory] of the [MainScreenViewModel].
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SVKApplication)
                val cargoTicketRepository = application.container.mainCargoTicketRepository
                CargoTicketScreenViewModel(
                    cargoTicketRepository = cargoTicketRepository,
                )
            }
        }
    }

    /**
     * Gets the cargo tickets from the [MainCargoTicketRepository].
     */
    private fun getCargoTickets() {
        _uiState.update {
            it.copy(
                cargoTickets = cargoTicketRepository.getNonProcessedCargoTickets()
            )
        }
    }
}