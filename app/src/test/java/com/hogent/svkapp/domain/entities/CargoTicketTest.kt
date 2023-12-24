package com.hogent.svkapp.domain.entities

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.reflect.typeOf

class CargoTicketTest {

    private val someRouteNumber: String = "1400123456"
    private val emptyRouteNumber: String = ""
    private val nonPositiveRouteNumber: String = "-1400123456"
    private val invalidFormatRouteNumber: String = "1400-123abc"

    private val somePlate = "1-ABC-123"
    private val emptyPlate = ""
    private val tooLongPlate = "This is a string with more than 40 characters. It should be long enough for your needs."

    private lateinit var image: Image

    @Before
    fun setUp() {
        image = mock()
    }

    @Test
    fun cargoTicket_Creation_Success() {
        assertTrue(
            CargoTicket.create(
                listOf(someRouteNumber),
                somePlate,
                listOf(image)
            ) is Result.Success<CargoTicket, CargoTicketError>
        )
    }

    @Test
    fun cargoTicket_Creation_EmptyRouteNumber_Failure() {
        assertTrue(
            CargoTicket.create(
                listOf(emptyRouteNumber),
                somePlate,
                listOf(image)
            ) is Result.Failure<CargoTicket, CargoTicketError>
        )
    }

    @Test
    fun cargoTicket_Creation_NonPositiveRouteNumber_Failure() {
        assertTrue(
            CargoTicket.create(
                listOf(nonPositiveRouteNumber),
                somePlate,
                listOf(image)
            ) is Result.Failure<CargoTicket, CargoTicketError>
        )
    }

    @Test
    fun cargoTicket_Creation_InvalidRouteNumber_Failure() {
        val result = CargoTicket.create(
            listOf(invalidFormatRouteNumber),
            somePlate,
            listOf(image)
        )

        assertTrue(
            result is Result.Failure<CargoTicket, CargoTicketError>
        )
    }

    @Test
    fun cargoTicket_Creation_EmptyPlate_Failure() {
        assertTrue(
            CargoTicket.create(
                listOf(someRouteNumber),
                emptyPlate,
                listOf(image)
            ) is Result.Failure<CargoTicket, CargoTicketError>
        )
    }

    @Test
    fun cargoTicket_Creation_TooLongPlate_Failure() {
        assertTrue(
            CargoTicket.create(
                listOf(someRouteNumber),
                tooLongPlate,
                listOf(image)
            ) is Result.Failure<CargoTicket, CargoTicketError>
        )
    }

    @Test
    fun cargoTicket_Creation_EmptyImage_Failure() {
        assertTrue(
            CargoTicket.create(
                listOf(someRouteNumber),
                somePlate,
                emptyList()
            ) is Result.Failure<CargoTicket, CargoTicketError>
        )
    }
}