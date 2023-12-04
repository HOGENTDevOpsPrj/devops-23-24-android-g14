package com.hogent.svkapp.data.sources.roomDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hogent.svkapp.domain.entities.ImageCollection
import com.hogent.svkapp.domain.entities.LicensePlate
import com.hogent.svkapp.domain.entities.RouteNumberCollection

@Entity(tableName = "cargoTicket")
data class DbCargoTicket(
    @PrimaryKey(autoGenerate = true) val id: String = "",
    @ColumnInfo(name="route_numbers") val routeNumbers: RouteNumberCollection,
    @ColumnInfo(name="licence_plate") val licensePlate: LicensePlate,
    @ColumnInfo(name="images") val images: ImageCollection,
)