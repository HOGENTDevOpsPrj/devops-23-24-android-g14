package com.hogent.svkapp.data.sources.roomDataBase

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hogent.svkapp.data.repositories.CargoTicketApiRepository
import com.hogent.svkapp.data.repositories.CargoTicketLocalRepository
import com.hogent.svkapp.domain.entities.CargoTicket
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * A manager for [CargoTicket]s that are not yet processed. This manager sends the [CargoTicket]s
 * to the server when the internet is available.
 *
 * @property cargoTicketApiRepository the [CargoTicketApiRepository] that is used to send the [CargoTicket]s to the server.
 * @property cargoTicketLocalRepository the [CargoTicketLocalRepository] that is used to save the [CargoTicket]s in a local roomDataBase.
 * @property context the application context.
 */
class UnprocessedCargoTicketsManager(
    private val cargoTicketApiRepository: CargoTicketApiRepository,
    private val cargoTicketLocalRepository: CargoTicketLocalRepository,
    context: Context,
) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isInternetAvailable = MutableLiveData<Boolean>()

    /**
     * A [LiveData] that indicates whether the internet is available.
     */
    val isInternetAvailable: LiveData<Boolean>
        get() = _isInternetAvailable

    init {
        checkInternetConnection()

        // Register a network callback to check the internet connection when the network changes.
        registerNetworkCallback()
    }

    private fun registerNetworkCallback() {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.d("UnprocessedCargoTicketsManager", "Connection available, sending unprocessed cargo tickets")
                _isInternetAvailable.postValue(true)
                sendUnprocessedCargoTickets()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
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

    @OptIn(DelicateCoroutinesApi::class)
    internal fun sendUnprocessedCargoTickets() {
        val cargoTickets = cargoTicketLocalRepository.getCargoTickets()
        cargoTickets.forEach {
            GlobalScope.launch {
                cargoTicketApiRepository.addCargoTicket(it)
            }
            cargoTicketLocalRepository.deleteCargoTicket(it)
        }
    }
}