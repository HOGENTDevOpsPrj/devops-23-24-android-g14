package com.hogent.svkapp.presentation.viewmodels

import com.hogent.svkapp.domain.entities.CargoTicket

/**
 * The state of the cargo ticket screen.
 */
data class CargoTicketScreenState(
    /**
     * The list of [CargoTicket]s.
     */
    val cargoTickets: List<CargoTicket> = emptyList(),
)