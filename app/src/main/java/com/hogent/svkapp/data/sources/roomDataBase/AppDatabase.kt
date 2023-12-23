package com.hogent.svkapp.data.sources.roomDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The Room database that contains the cargo ticket table.
 */
@Database(entities = [CargoTicketDatabaseEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Returns the DAO for the cargo ticket table.
     */
    abstract fun cargoTicketDao(): CargoTicketDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        /**
         * Returns an instance of the Room database.
         */
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, AppDatabase::class.java, "cargoTicket"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}