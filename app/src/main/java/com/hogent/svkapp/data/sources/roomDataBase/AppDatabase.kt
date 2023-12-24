package com.hogent.svkapp.data.sources.roomDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hogent.svkapp.domain.entities.ImageCollectionConverter
import com.hogent.svkapp.domain.entities.LicensePlateConverter
import com.hogent.svkapp.domain.entities.RouteNumberCollectionConverter

@Database(entities = [DbCargoTicket::class], version = 3, exportSchema = false)
@TypeConverters(RouteNumberCollectionConverter::class, LicensePlateConverter::class, ImageCollectionConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cargoTicketDao(): CargoTicketDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "cargoTicket"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}