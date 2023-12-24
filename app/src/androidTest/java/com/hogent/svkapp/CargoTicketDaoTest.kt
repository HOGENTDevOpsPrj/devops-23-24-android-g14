package com.hogent.svkapp

import android.content.Context
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hogent.svkapp.data.sources.roomDataBase.AppDatabase
import com.hogent.svkapp.data.sources.roomDataBase.CargoTicketDAO
import com.hogent.svkapp.data.sources.roomDataBase.asDbCargoTicket
import com.hogent.svkapp.data.sources.roomDataBase.asDomainCargoTicket
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.CargoTicketError
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.domain.entities.Result
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CargoTicketDaoTest {
    private lateinit var cargoTicketDao: CargoTicketDAO
    private lateinit var cargoTicketDb: AppDatabase
    private lateinit var context: Context

    private lateinit var cargoTicket1: Result.Success<CargoTicket, CargoTicketError>

    private lateinit var cargoTicket2: Result.Success<CargoTicket, CargoTicketError>

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        cargoTicketDb = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        cargoTicketDao = cargoTicketDb.cargoTicketDao()

        cargoTicket1 = CargoTicket.create(
            listOf("1400123", "1400456"),
            "1-ABC-123",
            listOf(
                Image(
                    bitmap = (
                            getDrawable(
                                context.resources,
                                R.drawable.svk_logo_met_slogan_black,
                                null
                            )!!.toBitmap()
                            )
                )
            )
        )
                as Result.Success

        cargoTicket2 = CargoTicket.create(
            listOf("1400234", "1400567"),
            "1-DEF-456",
            listOf(
                Image(
                    bitmap = (
                            getDrawable(
                                context.resources,
                                R.drawable.svk_logo_zonder_slogan,
                                null
                            )!!.toBitmap()
                            )
                )
            )
        )
                as Result.Success
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        cargoTicketDb.close()
    }

    // utility functions
    private fun addOneCargoTicketToDb() {
        cargoTicketDao.insert(cargoTicket1.value.asDbCargoTicket())
    }

    private fun addTwoCargoTicketsToDb() {
        cargoTicketDao.insert(cargoTicket1.value.asDbCargoTicket())
        cargoTicketDao.insert(cargoTicket2.value.asDbCargoTicket())
    }

//    @Test
//    @Throws(Exception::class)
//    fun daoInert_insertTaskIntoDB() = runBlocking {
//        addOneCargoTicketToDb()
//        val allItems = cargoTicketDao.getAll()
//        assertTrue(allItems.map {
//            it.asDomainCargoTicket()
//        }.contains(cargoTicket1.value))
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun daoGetAllTasks_returnsAllTasksFromDB() = runBlocking {
//        addTwoCargoTicketsToDb()
//        val allItems = cargoTicketDao.getAll()
//        assertTrue(allItems.map {
//            it.asDomainCargoTicket()
//        }.contains(cargoTicket1.value))
//        assertTrue(allItems.map {
//            it.asDomainCargoTicket()
//        }.contains(cargoTicket2.value))
//    }
}