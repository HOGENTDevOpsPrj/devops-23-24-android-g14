package com.hogent.svkapp.data.sources.roomDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hogent.svkapp.domain.entities.Image

@Entity(tableName = "cargoTicket")
data class CargoTicket(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name="route_numbers") val routeNumbers: List<String>,
    @ColumnInfo(name="licence_plate") val licensePlate: String,
    @ColumnInfo(name="images") val images: List<Image>,
)