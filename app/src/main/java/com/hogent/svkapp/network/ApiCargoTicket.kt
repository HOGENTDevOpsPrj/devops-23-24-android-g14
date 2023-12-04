package com.hogent.svkapp.network

import com.hogent.svkapp.domain.entities.CargoTicket
import kotlinx.serialization.Serializable

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
            return ApiCargoTicket(
                loadReceiptNumber = "2346",
                routeNumbers = cargoTicket.routeNumbers.value.map { it.value.toString() },
                licensePlate = cargoTicket.licensePlate.value,
                imageUrls = cargoTicket.images.value.map { "http://example.com/image1.jpg" },
                freightLoaderId = "eb9ac197-23c5-42b0-9641-a5fb4fa92336",
                registrationDateTime = "2023-12-01T12:00:00"
            )
        }
    }
}
