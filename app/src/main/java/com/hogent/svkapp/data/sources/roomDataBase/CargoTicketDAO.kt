package com.hogent.svkapp.data.sources.roomDataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CargoTicketDAO {

    @Query("SELECT * FROM cargoTicket ORDER BY licence_plate ASC")
    fun getAll(): List<DbCargoTicket>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dbCargoTicket: DbCargoTicket)

    @Delete
    fun delete(dbCargoTicket: DbCargoTicket)
}