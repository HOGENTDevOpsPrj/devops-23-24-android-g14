package com.hogent.svkapp.data.repositories

import android.content.Context
import com.hogent.svkapp.data.sources.roomDataBase.CargoTicketDAO
import com.hogent.svkapp.data.sources.roomDataBase.CargoTicketDatabaseEntity.Companion.toCargoTicket
import com.hogent.svkapp.data.sources.roomDataBase.CargoTicketDatabaseEntity.Companion.toCargoTicketDatabaseEntity
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.util.CustomResult

/**
 * A repository for [CargoTicket]s. This repository saves the [CargoTicket]s in a local roomDataBase.
 */
interface CargoTicketLocalRepository {
    /**
     * Adds a [CargoTicket] to the repository.
     *
     * @param cargoTicket the [CargoTicket] to add.
     */
    fun addCargoTicket(cargoTicket: CargoTicket)

    /**
     * Gets all the [CargoTicket]s from the repository.
     *
     * @return a list of [CargoTicket]s.
     */
    fun getCargoTickets(): List<CargoTicket>

    /**
     * Deletes a [CargoTicket] from the repository.
     *
     * @param cargoTicket the [CargoTicket] to delete.
     */
    fun deleteCargoTicket(cargoTicket: CargoTicket)
}

/**
 * A repository for [CargoTicket]s. This repository saves the [CargoTicket]s in a local roomDataBase.
 *
 * @property cargoTicketDAO the [CargoTicketDAO] that is used to save the [CargoTicket]s in a local roomDataBase.
 * @property context the [Context] that is used to get Internet connection status.
 */
class CargoTicketLocalRepositoryImpl(
    private val cargoTicketDAO: CargoTicketDAO, private val context: Context
) : CargoTicketLocalRepository {
    override fun addCargoTicket(cargoTicket: CargoTicket) {
        cargoTicketDAO.insert(
            cargoTicket.toCargoTicketDatabaseEntity(context)
        )
    }

    override fun getCargoTickets(): List<CargoTicket> {
        val dbCargoTickets = cargoTicketDAO.getAll()

        val cargoTicketResults = dbCargoTickets.map {
            it.toCargoTicket()
        }

        val cargoTickets = cargoTicketResults.mapNotNull { cargoTicketResult ->
            if (cargoTicketResult is CustomResult.Success) {
                cargoTicketResult.value
            } else {
                null
            }
        }

        return cargoTickets
    }

    override fun deleteCargoTicket(cargoTicket: CargoTicket) {
        cargoTicketDAO.delete(
            cargoTicket.toCargoTicketDatabaseEntity(context)
        )
    }
}