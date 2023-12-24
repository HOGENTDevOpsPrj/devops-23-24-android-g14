package com.hogent.svkapp.data.repositories

import com.hogent.svkapp.data.sources.roomDataBase.UnprocessedCargoTicketsManager
import com.hogent.svkapp.domain.entities.CargoTicket


/**
 * A repository for [CargoTicket]s.
 */
interface MainCargoTicketRepository {
    /**
     * Adds a [CargoTicket] to the repository.
     *
     * @param cargoTicket the [CargoTicket] to add.
     */
    suspend fun addCargoTicket(cargoTicket: CargoTicket)

    /**
     * Gets all the [CargoTicket]s from the repository.
     *
     * @return a list of [CargoTicket]s.
     */
    fun getNonProcessedCargoTickets(): List<CargoTicket>
}

/**
 * A repository for [CargoTicket]s. This repository saves the [CargoTicket]s in a local roomDataBase
 * and sends them to the server when the internet is available.
 *
 * @property unprocessedCargoTicketsManager the [UnprocessedCargoTicketsManager] that is used to check if the internet is available.
 * @property cargoTicketApiRepository the [CargoTicketApiRepositoryImpl] that is used to send the [CargoTicket]s to the server.
 * @property cargoTicketLocalRepository the [CargoTicketLocalRepositoryImpl] that is used to save the [CargoTicket]s in a local roomDataBase.
 */
class MainCargoTicketRepositoryImpl(
    private val unprocessedCargoTicketsManager: UnprocessedCargoTicketsManager,
    private val cargoTicketApiRepository: CargoTicketApiRepositoryImpl,
    private val cargoTicketLocalRepository: CargoTicketLocalRepositoryImpl
) : MainCargoTicketRepository {
    override suspend fun addCargoTicket(cargoTicket: CargoTicket) {
        if (unprocessedCargoTicketsManager.isInternetAvailable.value == true) {
            cargoTicketApiRepository.addCargoTicket(cargoTicket)
        } else {
            cargoTicketLocalRepository.addCargoTicket(cargoTicket)
        }
    }

    override fun getNonProcessedCargoTickets(): List<CargoTicket> {
        return cargoTicketLocalRepository.getCargoTickets()
    }
}



