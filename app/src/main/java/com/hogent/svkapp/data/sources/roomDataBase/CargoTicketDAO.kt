package com.hogent.svkapp.data.sources.roomDataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CargoTicketDAO {

    @Query("SELECT * FROM cargoTicket")
    fun getAll(): List<CargoTicket>

    @Insert
    fun insertAll(vararg cargoTicket: CargoTicket)

    @Delete
    fun delete(cargoTicket: CargoTicket)
}