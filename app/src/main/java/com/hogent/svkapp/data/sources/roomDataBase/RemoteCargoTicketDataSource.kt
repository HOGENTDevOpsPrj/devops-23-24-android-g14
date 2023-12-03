package com.hogent.svkapp.data.sources.roomDataBase

import NetworkUtils
import android.content.Context
import com.hogent.svkapp.data.sources.CargoTicketDataSource
import com.hogent.svkapp.domain.entities.AndroidLogger
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.Logger
import com.hogent.svkapp.network.LadingApi
import com.hogent.svkapp.data.sources.roomDataBase.CargoTicket as DataCargoTicket






class RemoteCargoTicketDataSource(private val logger: Logger = AndroidLogger(), private val context: Context) :
    CargoTicketDataSource {
    private val db = AppDatabase.getInstance(context);
    private val dao = db?.cargoTicketDAO();
    private val networkUtils = NetworkUtils(context);
    override fun addCargoTicket(cargoTicket: CargoTicket) {
        if (networkUtils.isInternetAvailable.value == true) {
            LadingApi.retrofitService.createLading(cargoTicket)
        } else{
            dao?.insert(
                DataCargoTicket(
                    routeNumbers = cargoTicket.routeNumbers, licensePlate = cargoTicket
                        .licensePlate, images = cargoTicket.images
                )
            )
        }
    }

    override fun getCargoTickets(): List<CargoTicket> {
        //TODO
        return listOf<CargoTicket>();
    }


}