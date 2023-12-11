package com.hogent.svkapp.network

import android.util.Log
import com.hogent.svkapp.domain.entities.CargoTicket
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
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
            return ApiCargoTicket(
                loadReceiptNumber = "2346",
                routeNumbers = cargoTicket.routeNumbers.value.map { it.value.toString() },
                licensePlate = cargoTicket.licensePlate.value,
                imageUrls = cargoTicket.images.value.map {
                    val fotoUploader = AzureBlobUploader("https://svkstorageg14.blob.core.windows.net/");
                    val sasToken =
                        "?sv=2022-11-02&ss=b&srt=co&sp=rwdlaciytfx&se=2024-01-29T23:12:27Z&st=2023-12-05T15" +
                                ":12:27Z&spr=https&sig=yGX%2FMbbmyTAu4ACKxf8MK2RB1GJiR1wyPv%2FXyRz0ReE%3D"
                    fotoUploader.uploadImage(it.id, sasToken, it.bitmap)
                },
                freightLoaderId = "5e548527-426b-43d7-b90a-ffee739e1900",
                registrationDateTime = LocalDateTime.now(ZoneId.systemDefault()).format(
                    DateTimeFormatter.ofPattern
                        ("yyyy-MM-dd'T'HH:mm:ss")
                )
            )
        }
    }
}
