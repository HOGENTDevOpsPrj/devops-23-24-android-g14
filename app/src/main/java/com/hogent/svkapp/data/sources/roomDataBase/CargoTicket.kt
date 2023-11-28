package com.hogent.svkapp.data.sources.roomDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cargoTicket")
data class CargoTicket(
    @PrimaryKey(autoGenerate = true) val id: String = "",
    @ColumnInfo(name="route_numbers") val routeNumbers: List<String>,
    @ColumnInfo(name="licence_plate") val licensePlate: String,
    @ColumnInfo(name="images") val images: List<String>,
)