package com.hogent.svkapp.network

import com.hogent.svkapp.domain.entities.CargoTicket
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * A cargo ticket. This class is used to send the cargo ticket to the server.
 *
 * @property loadReceiptNumber the load receipt number of the cargo.
 * @property routeNumbers the route numbers of the cargo.
 * @property licensePlate the license plate of the cargo.
 * @property imageUrls the image urls of the cargo.
 * @property freightLoaderId the ID of the freight loader.
 * @property registrationDateTime the date and time of the registration.
 */
@Serializable
data class ApiCargoTicket(
    val loadReceiptNumber: String,
    val routeNumbers: List<String>,
    val licensePlate: String,
    val imageUrls: List<String>,
    val freightLoaderId: String,
    val registrationDateTime: String
)

/**
 * A converter for [CargoTicket]s. This class is used to convert [CargoTicket]s to [ApiCargoTicket]s.
 */
class CargoTicketConverter {
    companion object {
        /**
         * Converts a [CargoTicket] to an [ApiCargoTicket].
         *
         * @receiver the [CargoTicket] to convert
         * @return the [ApiCargoTicket]
         */
        fun CargoTicket.convertToApiCargoTicket(): ApiCargoTicket {
            return ApiCargoTicket(
                loadReceiptNumber = loadReceiptNumber.value,
                routeNumbers = routeNumbers.value.map { it.value },
                licensePlate = licensePlate.value,
                imageUrls = images.value.map {
                    val imageUploader = AzureBlobUploader("https://svkstorageg14.blob.core.windows.net/")
                    val sasToken =
                        "?sv=2022-11-02&ss=b&srt=co&sp=rwdlaciytfx&se=2024-01-29T23:12:27Z&st=2023-12-05T15" + ":12:27Z&spr=https&sig=yGX%2FMbbmyTAu4ACKxf8MK2RB1GJiR1wyPv%2FXyRz0ReE%3D"
                    imageUploader.uploadImage(it.id, sasToken, it.bitmap)
                },
                freightLoaderId = freightLoaderId.value,
                registrationDateTime = LocalDateTime.now(ZoneId.systemDefault()).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                )
            )
        }
    }
}
