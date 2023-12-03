package com.hogent.svkapp.network

import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.RouteNumber
import com.hogent.svkapp.domain.entities.RouteNumberCollection
import kotlinx.serialization.Serializable

@Serializable
data class ApiCargoTicket (
    val routeNumbers: List<Int>,
    val licensePlate: String,
    val images: List<String>,
)

