package com.hogent.svkapp.data.sources.roomDataBase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.CargoTicketError
import com.hogent.svkapp.domain.entities.Image
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime

/**
 * A ticket for cargo. This class is used to store the cargo ticket in the database.
 *
 * @property loadReceiptNumber the load receipt number of the cargo ticket. This is the primary key of the cargo ticket.
 * @property routeNumbers the route numbers of the cargo ticket
 * @property licensePlate the license plate of the cargo ticket
 * @property imagePaths the paths of the images of the cargo ticket
 * @property freightLoaderId the id of the freight loader of the cargo ticket
 * @property registrationDateTime the registration date and time of the cargo ticket
 */
@Entity(tableName = "cargoTicket")
data class CargoTicketDatabaseEntity(
    @PrimaryKey @ColumnInfo(name = "load_receipt_number") val loadReceiptNumber: String,
    @ColumnInfo(name = "route_numbers") val routeNumbers: List<String>,
    @ColumnInfo(name = "licence_plate") val licensePlate: String,
    @ColumnInfo(name = "image_paths") val imagePaths: List<String>,
    @ColumnInfo(name = "freight_loader_id") val freightLoaderId: String,
    @ColumnInfo(name = "registration_date_time") val registrationDateTime: String
) {
    companion object {
        /**
         * Converts the [CargoTicketDatabaseEntity] to a [CargoTicket].
         *
         * @receiver the [CargoTicketDatabaseEntity] to convert
         * @return a Result with the [CargoTicket] if the conversion was successful, a Result with a [CargoTicketError]
         * otherwise.
         */
        fun CargoTicketDatabaseEntity.toCargoTicket(): com.hogent.svkapp.domain.entities.Result<CargoTicket, CargoTicketError> {
            return CargoTicket.create(
                loadReceiptNumber = loadReceiptNumber,
                routeNumbers = routeNumbers,
                licensePlate = licensePlate,
                images = imagePaths.mapNotNull { imagePath ->
                    val bitmap = getBitmapFromInternalStorage(imagePath)
                    if (bitmap != null) {
                        Image(bitmap = bitmap)
                    } else {
                        null
                    }
                },
                freightLoaderId = freightLoaderId,
                registrationDateTime = LocalDateTime.parse(registrationDateTime)
            )
        }

        /**
         * Converts the [CargoTicket] to a [CargoTicketDatabaseEntity].
         *
         * @receiver the [CargoTicket] to convert
         * @param context the context of the application
         * @return the [CargoTicketDatabaseEntity]
         */
        fun CargoTicket.toCargoTicketDatabaseEntity(context: Context): CargoTicketDatabaseEntity {
            return CargoTicketDatabaseEntity(
                loadReceiptNumber = loadReceiptNumber.value,
                routeNumbers = routeNumbers.value.map { routeNumber -> routeNumber.value },
                licensePlate = licensePlate.value,
                imagePaths = images.value.map { image ->
                    val imagePath = saveImageToInternalStorage(
                        context = context,
                        bitmap = image.bitmap,
                        fileName = "${loadReceiptNumber.value}_${image.bitmap.hashCode()}.png"
                    )
                    imagePath
                },
                freightLoaderId = freightLoaderId.value,
                registrationDateTime = registrationDateTime.toString()
            )
        }

        private fun getBitmapFromInternalStorage(filePath: String): Bitmap? {
            val file = File(filePath)
            return if (file.exists()) {
                BitmapFactory.decodeFile(file.absolutePath)
            } else {
                null
            }
        }

        private fun saveImageToInternalStorage(context: Context, bitmap: Bitmap, fileName: String): String {
            val directory = context.filesDir
            val file = File(directory, fileName)

            try {
                val stream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return file.absolutePath
        }
    }
}
