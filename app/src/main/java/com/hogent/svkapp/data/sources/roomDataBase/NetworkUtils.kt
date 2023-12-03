package com.hogent.svkapp.data.sources.roomDataBase

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.network.ApiCargoTicket
import com.hogent.svkapp.network.CargoTicketApiService
import com.hogent.svkapp.network.CargoTicketConverter.Companion.convertToApiCargoTicket
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NetworkUtils(
    private val cargoTicketApiService: CargoTicketApiService,
    context: Context,
) {

    private val db = AppDatabase.getInstance(context)
    private val dao = db?.cargoTicketDAO()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean>
        get() = _isInternetAvailable

    init {
        // Controleer de huidige status van de internetverbinding
        checkInternetConnection()

        // Registreer de NetworkCallback om wijzigingen in de internetverbinding te detecteren
        registerNetworkCallback()
    }


    private fun registerNetworkCallback() {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d("com.hogent.svkapp.data.sources.roomDataBase.NetworkUtils", "Internet beschikbaar")
                _isInternetAvailable.postValue(true)
                sendUnprocessedCargoTickets()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                Log.d("com.hogent.svkapp.data.sources.roomDataBase.NetworkUtils", "Internet niet beschikbaar")
                _isInternetAvailable.postValue(false)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    private fun checkInternetConnection() {
        val networkCapabilities = connectivityManager.activeNetwork ?: return
        val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities)
        val isConnected = activeNetwork?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        _isInternetAvailable.postValue(isConnected)
    }

    private fun sendUnprocessedCargoTickets() {
        val ladingen = dao?.getAll()
        ladingen?.forEach {
            GlobalScope.launch {
                cargoTicketApiService.postCargoTicket(
                    convertToApiCargoTicket(
                        CargoTicket(
                            routeNumbers = it.routeNumbers, images = it.images,
                            licensePlate = it.licensePlate
                        )
                    )
                )
            }
            dao?.delete(it)
        }
    }
}
