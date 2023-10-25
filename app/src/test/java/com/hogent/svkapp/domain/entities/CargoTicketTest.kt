package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.domain.ValidationError
import com.hogent.svkapp.domain.ValidationResult
import com.hogent.svkapp.domain.Validator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CargoTicketTest {
    private lateinit var validator: Validator

    @Before
    fun setup() {
        validator = mock()
    }

    @Test
    fun `create with valid inputs creates cargo ticket`() {
        val routeNumber = "123"
        val licensePlate = "ABC123"
        val images = listOf<Image>()

        whenever(validator.validateRouteNumber(any())).thenReturn(ValidationResult.Success)
        whenever(validator.validateLicensePlate(any())).thenReturn(ValidationResult.Success)
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Success)

        val result = CargoTicket.create(routeNumber, licensePlate, images, validator)

        assertTrue(result is CreationResult.Success)
        assertEquals(
            routeNumber.trim().filter { !it.isWhitespace() }.toInt(),
            (result as CreationResult.Success).cargoTicket.routeNumber
        )
        assertEquals(
            licensePlate.trim().filter { !it.isWhitespace() }.uppercase(),
            result.cargoTicket.licensePlate
        )
    }

    @Test
    fun `create with invalid inputs returns appropriate errors`() {
        val routeNumber = "invalid"
        val licensePlate = "invalid"
        val images = listOf<Image>()

        val routeError = ValidationResult.Error(ValidationError.INVALID_ROUTE_NUMBER)
        val licenseError = ValidationResult.Error(ValidationError.EMPTY_LICENSE_PLATE)

        whenever(validator.validateRouteNumber(any())).thenReturn(routeError)
        whenever(validator.validateLicensePlate(any())).thenReturn(licenseError)
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Success)

        val result = CargoTicket.create(routeNumber, licensePlate, images, validator)

        assertTrue(result is CreationResult.Failure)
        assertTrue(
            (result as CreationResult.Failure).errors.containsAll(
                listOf(
                    ValidationError.INVALID_ROUTE_NUMBER, ValidationError.EMPTY_LICENSE_PLATE
                )
            )
        )
    }
}
