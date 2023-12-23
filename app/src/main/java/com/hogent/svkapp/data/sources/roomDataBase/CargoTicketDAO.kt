package com.hogent.svkapp.data.sources.roomDataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the cargo ticket table.
 */
@Dao
interface CargoTicketDAO {

    /**
     * Returns all the cargo tickets from the table.
     *
     * @return all the cargo tickets from the table.
     */
    @Query("SELECT * FROM cargoTicket")
    fun getAll(): List<CargoTicketDatabaseEntity>

    /**
     * Inserts a cargo ticket in the table. If the cargo ticket already exists, replace it.
     *
     * @param cargoTicketDatabaseEntity the cargo ticket to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cargoTicketDatabaseEntity: CargoTicketDatabaseEntity)

    /**
     * Deletes a cargo ticket from the table.
     *
     * @param cargoTicketDatabaseEntity the cargo ticket to delete
     */
    @Delete
    fun delete(cargoTicketDatabaseEntity: CargoTicketDatabaseEntity)
}