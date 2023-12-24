package com.hogent.svkapp.data.sources.roomDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.ImageCollection
import com.hogent.svkapp.domain.entities.LicensePlate
import com.hogent.svkapp.domain.entities.RouteNumberCollection

@Entity(tableName = "cargoTicket")
data class DbCargoTicket(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "route_numbers") val routeNumbers: RouteNumberCollection,
    @ColumnInfo(name = "licence_plate") val licensePlate: LicensePlate,
    @ColumnInfo(name = "images") val images: ImageCollection,
)

fun DbCargoTicket.asDomainCargoTicket(): CargoTicket {
    return CargoTicket(
        routeNumbers = this.routeNumbers,
        licensePlate = this.licensePlate,
        images = this.images,
    )
}

fun CargoTicket.asDbCargoTicket(): DbCargoTicket {
    return DbCargoTicket(
        routeNumbers = this.routeNumbers,
        licensePlate = this.licensePlate,
        images = this.images,
    )
}

fun List<DbCargoTicket>.asDomainCargoTickeas(): List<CargoTicket> {
    return this.map {
        CargoTicket(
            routeNumbers = it.routeNumbers,
            licensePlate = it.licensePlate,
            images = it.images,
        )
    }
}