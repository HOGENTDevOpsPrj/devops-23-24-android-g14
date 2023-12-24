package com.hogent.svkapp.data.repositories

import android.content.Context
import com.hogent.svkapp.data.sources.CargoTicketDataSource
import com.hogent.svkapp.data.sources.roomDataBase.AppDatabase
import com.hogent.svkapp.data.sources.roomDataBase.DbCargoTicket
import com.hogent.svkapp.data.sources.roomDataBase.NetworkUtils
import com.hogent.svkapp.data.sources.roomDataBase.asDomainCargoTickeas
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.network.CargoTicketApiService
import com.hogent.svkapp.network.CargoTicketConverter.Companion.convertToApiCargoTicket


interface CargoTicketRepository {
    fun getCargoTickets(): List<CargoTicket>
    suspend fun addCargoTicket(cargoTicket: CargoTicket)
}

class RoomCargoTicketRepository(
    context: Context,
    private val cargoTicketApiService: CargoTicketApiService,
) :
    CargoTicketRepository {
    private val db = AppDatabase.getInstance(context)
    private val dao = db?.cargoTicketDao()
    private val networkUtils = NetworkUtils(
        cargoTicketApiService,
        context,
    )

    override suspend fun addCargoTicket(cargoTicket: CargoTicket) {
        if (networkUtils.isInternetAvailable.value == true) {
            cargoTicketApiService.postCargoTicket(convertToApiCargoTicket(cargoTicket))
        } else {
            dao?.insert(
                DbCargoTicket(
                    routeNumbers = cargoTicket.routeNumbers, licensePlate = cargoTicket
                        .licensePlate, images = cargoTicket.images
                )
            )
        }
    }

    override fun getCargoTickets(): List<CargoTicket> {
        return dao?.getAll()?.asDomainCargoTickeas() ?: emptyList()
    }
}

/**
 * A repository for [CargoTicket]s.
 *
 * @property cargoTicketDataSource the [CargoTicketDataSource] that is used to store the [CargoTicket]s.
 */
class LocalCargoTicketRepository(
    private val cargoTicketDataSource: CargoTicketDataSource,
) : CargoTicketRepository {

    /**
     * Gets all [CargoTicket]s from the repository.
     */
    override fun getCargoTickets(): List<CargoTicket> {
        return cargoTicketDataSource.getCargoTickets()
    }

    /**
     * Adds a [CargoTicket] to the repository.
     *
     * @param cargoTicket the [CargoTicket] to add.
     */

    // TODO: Make suspend function move to coroutine
    override suspend fun addCargoTicket(cargoTicket: CargoTicket): Unit =
        cargoTicketDataSource.addCargoTicket(cargoTicket)
}

class ApiCargoTicketRepository(
    private val cargoTicketApiService: CargoTicketApiService
) : CargoTicketRepository {

    /**
     * Gets all [CargoTicket]s from the repository.
     */
    override fun getCargoTickets(): List<CargoTicket> {
        return emptyList()
    }

    /**
     * Adds a [CargoTicket] to the repository.
     *
     * @param cargoTicket the [CargoTicket] to add.
     */
    override suspend fun addCargoTicket(cargoTicket: CargoTicket) {
        val apiCargoTicket = convertToApiCargoTicket(cargoTicket)
        cargoTicketApiService.postCargoTicket(apiCargoTicket)
    }
}

