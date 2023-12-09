package com.hogent.svkapp.network

import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.RouteNumber
import com.hogent.svkapp.domain.entities.RouteNumberCollection
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class ApiCargoTicket (
    val loadReceiptNumber: String,
    val routeNumbers: List<String>,
    val licensePlate: String,
    val imageUrls: List<String>,
    val freightLoaderId: String,
    val registrationDateTime: String
)

class CargoTicketConverter {
    companion object {
        fun convertToApiCargoTicket(cargoTicket: CargoTicket): ApiCargoTicket {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            return ApiCargoTicket(
                loadReceiptNumber = "2346",
                routeNumbers = cargoTicket.routeNumbers.value.map { it.value.toString() },
                licensePlate = cargoTicket.licensePlate.value,
                imageUrls = cargoTicket.images.value.map { "https://picsum.photos/640/480/?image=1033" },
                freightLoaderId = "eb9ac197-23c5-42b0-9641-a5fb4fa92336",
                registrationDateTime = LocalDateTime.now().format(formatter)
            )
        }
    }
}
