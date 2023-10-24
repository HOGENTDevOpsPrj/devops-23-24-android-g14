package com.hogent.svkapp.features.create_ticket.domain.entities

import com.hogent.svkapp.domain.ValidationError
import com.hogent.svkapp.domain.ValidationResult
import com.hogent.svkapp.domain.Validator
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.domain.entities.CreationResult
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CargoTicketTest {
    @Mock
    lateinit var mockValidator: Validator

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Ticket creation success`() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(null)
        `when`(mockValidator.validateLicensePlate(anyString())).thenReturn(null)
        `when`(mockValidator.validateImages(anyList())).thenReturn(null)

        val result = CargoTicket.create(
            routeNumber = "123",
            licensePlate = "AB-123-CD",
            images = emptyList(),
            validator = mockValidator
        )

        assertTrue(result is CreationResult.Success)
        assertEquals(123, (result as CreationResult.Success).cargoTicket.routeNumber)
        assertEquals("AB-123-CD", result.cargoTicket.licensePlate)
        assertTrue(result.cargoTicket.images.isEmpty())
    }

    @Test
    fun `Ticket creation with route error`() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(
            ValidationResult.Error(
                ValidationError.INVALID_ROUTE_NUMBER
            )
        )

        val result = CargoTicket.create(
            routeNumber = "XYZ",
            licensePlate = "AB-123-CD",
            images = emptyList(),
            validator = mockValidator
        )

        assertTrue(result is CreationResult.Failure)
        val errors = (result as CreationResult.Failure).errors
        assertTrue(errors.contains(ValidationError.INVALID_ROUTE_NUMBER))
    }

    @Test
    fun `Ticket creation with license plate error`() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(null)
        `when`(mockValidator.validateLicensePlate(anyString())).thenReturn(
            ValidationResult.Error(
                ValidationError.LONG_LICENSE_PLATE
            )
        )

        val result = CargoTicket.create(
            routeNumber = "123",
            licensePlate = "AB-123-CD",
            images = emptyList(),
            validator = mockValidator
        )

        assertTrue(result is CreationResult.Failure)
        val errors = (result as CreationResult.Failure).errors
        assertTrue(errors.contains(ValidationError.LONG_LICENSE_PLATE))
    }

    @Test
    fun `Ticket creation with images error`() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(null)
        `when`(mockValidator.validateLicensePlate(anyString())).thenReturn(null)
        `when`(mockValidator.validateImages(anyList())).thenReturn(
            ValidationResult.Error(
                ValidationError.EMPTY_IMAGES
            )
        )

        val result = CargoTicket.create(
            routeNumber = "123",
            licensePlate = "AB-123-CD",
            images = emptyList(),
            validator = mockValidator
        )

        assertTrue(result is CreationResult.Failure)
        val errors = (result as CreationResult.Failure).errors
        assertTrue(errors.contains(ValidationError.EMPTY_IMAGES))
    }

    @Test
    fun `Ticket creation with multiple errors`() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(
            ValidationResult.Error(
                ValidationError.INVALID_ROUTE_NUMBER
            )
        )
        `when`(mockValidator.validateLicensePlate(anyString())).thenReturn(
            ValidationResult.Error(
                ValidationError.LONG_LICENSE_PLATE
            )
        )
        `when`(mockValidator.validateImages(anyList())).thenReturn(
            ValidationResult.Error(
                ValidationError.EMPTY_IMAGES
            )
        )

        val result = CargoTicket.create(
            routeNumber = "XYZ",
            licensePlate = "AB-123-CD",
            images = emptyList(),
            validator = mockValidator
        )

        assertTrue(result is CreationResult.Failure)
        val errors = (result as CreationResult.Failure).errors
        assertTrue(errors.contains(ValidationError.INVALID_ROUTE_NUMBER))
        assertTrue(errors.contains(ValidationError.LONG_LICENSE_PLATE))
        assertTrue(errors.contains(ValidationError.EMPTY_IMAGES))
    }
}
