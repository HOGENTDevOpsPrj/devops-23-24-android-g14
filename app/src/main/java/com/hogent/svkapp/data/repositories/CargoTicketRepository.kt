package com.hogent.svkapp.data.repositories

import android.content.Context
import com.hogent.svkapp.data.sources.roomDataBase.CargoTicketDAO
import com.hogent.svkapp.data.sources.roomDataBase.CargoTicketDatabaseEntity.Companion.toCargoTicket
import com.hogent.svkapp.data.sources.roomDataBase.CargoTicketDatabaseEntity.Companion.toCargoTicketDatabaseEntity
import com.hogent.svkapp.data.sources.roomDataBase.UnprocessedCargoTicketsManager
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.network.CargoTicketApiService
import com.hogent.svkapp.network.CargoTicketConverter.Companion.convertToApiCargoTicket


/**
 * A repository for [CargoTicket]s.
 */
interface CargoTicketRepository {
    /**
     * Adds a [CargoTicket] to the repository.
     */
    suspend fun addCargoTicket(cargoTicket: CargoTicket)

    /**
     * Gets all the [CargoTicket]s from the repository.
     */
    fun getNonProcessedCargoTickets(): List<CargoTicket>
}

/**
 * A repository for [CargoTicket]s. This repository saves the [CargoTicket]s in a local roomDataBase
 * and sends them to the server when the internet is available.
 *
 * @property unprocessedCargoTicketsManager the [UnprocessedCargoTicketsManager] that is used to check if the internet is available.
 * @property cargoTicketApiRepository the [CargoTicketApiRepository] that is used to send the [CargoTicket]s to the server.
 * @property cargoTicketLocalRepository the [CargoTicketLocalRepository] that is used to save the [CargoTicket]s in a local roomDataBase.
 */
class MainCargoTicketRepository(
    private val unprocessedCargoTicketsManager: UnprocessedCargoTicketsManager,
    private val cargoTicketApiRepository: CargoTicketApiRepository,
    private val cargoTicketLocalRepository: CargoTicketLocalRepository
) : CargoTicketRepository {
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

/**
 * A repository for [CargoTicket]s. This repository sends the [CargoTicket]s to the server.
 *
 * @property cargoTicketApiService the [CargoTicketApiService] that is used to send the [CargoTicket]s to the server.
 */
class CargoTicketApiRepository(
    private val cargoTicketApiService: CargoTicketApiService
) {
    /**
     * Adds a [CargoTicket] to the repository.
     */
    suspend fun addCargoTicket(cargoTicket: CargoTicket) {
        val apiCargoTicket = cargoTicket.convertToApiCargoTicket()
        cargoTicketApiService.postCargoTicket(apiCargoTicket)
    }
}

/**
 * A repository for [CargoTicket]s. This repository saves the [CargoTicket]s in a local roomDataBase.
 */
class CargoTicketLocalRepository(
    private val cargoTicketDAO: CargoTicketDAO, private val context: Context
) {
    /**
     * Adds a [CargoTicket] to the repository.
     */
    fun addCargoTicket(cargoTicket: CargoTicket) {
        cargoTicketDAO.insert(
            cargoTicket.toCargoTicketDatabaseEntity(context)
        )
    }

    /**
     * Gets all the [CargoTicket]s from the repository.
     */
    fun getCargoTickets(): List<CargoTicket> {
        val dbCargoTickets = cargoTicketDAO.getAll()

        val cargoTicketResults = dbCargoTickets.map {
            it.toCargoTicket()
        }

        val cargoTickets = cargoTicketResults.mapNotNull { cargoTicketResult ->
            if (cargoTicketResult is com.hogent.svkapp.domain.entities.Result.Success) {
                cargoTicketResult.value
            } else {
                null
            }
        }

        return cargoTickets
    }

    /**
     * Deletes a [CargoTicket] from the repository.
     *
     * @param cargoTicket the [CargoTicket] to delete.
     */
    fun deleteCargoTicket(cargoTicket: CargoTicket) {
        cargoTicketDAO.delete(
            cargoTicket.toCargoTicketDatabaseEntity(context)
        )
    }
}