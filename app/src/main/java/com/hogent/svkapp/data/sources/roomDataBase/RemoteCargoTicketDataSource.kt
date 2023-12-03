package com.hogent.svkapp.data.sources.roomDataBase

import android.content.Context
import com.hogent.svkapp.data.sources.CargoTicketDataSource
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.network.CargoTicketApiService
import com.hogent.svkapp.data.sources.roomDataBase.DbCargoTicket as DataCargoTicket


class RemoteCargoTicketDataSource(
    private val context: Context,
    private val cargoTicketApiService: CargoTicketApiService,
) :
    CargoTicketDataSource {
    private val db = AppDatabase.getInstance(context)
    private val dao = db?.cargoTicketDAO()
    private val networkUtils = NetworkUtils(
        cargoTicketApiService,
        context,
    )

    override suspend fun addCargoTicket(cargoTicket: CargoTicket) {
        if (networkUtils.isInternetAvailable.value == true) {
            cargoTicketApiService.postCargoTicket(cargoTicket)
        } else {
            dao?.insert(
                DataCargoTicket(
                    routeNumbers = cargoTicket.routeNumbers, licensePlate = cargoTicket
                        .licensePlate, images = cargoTicket.images
                )
            )
        }
    }

    override fun getCargoTickets(): List<CargoTicket> {
        // TODO
        return listOf<CargoTicket>()
    }


}