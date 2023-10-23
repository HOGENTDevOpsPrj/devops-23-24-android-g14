package com.hogent.svkapp.features.create_ticket.domain.entities

import com.hogent.svkapp.domain.Validator
import com.hogent.svkapp.domain.entities.CreationResult
import com.hogent.svkapp.domain.entities.ErrorType
import com.hogent.svkapp.domain.entities.Ticket
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TicketTest {
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

        val result = Ticket.create(mockValidator, "123", "AB-123-CD", emptyList())

        assertTrue(result is CreationResult.Success)
        assertEquals(123, (result as CreationResult.Success).ticket.routeNumber)
        assertEquals("AB-123-CD", result.ticket.licensePlate)
        assertTrue(result.ticket.images.isEmpty())
    }

    @Test
    fun `Ticket creation with route error`() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(ErrorType.INVALID_ROUTE_NUMBER)

        val result = Ticket.create(mockValidator, "XYZ", "AB-123-CD", emptyList())

        assertTrue(result is CreationResult.Failure)
        assertTrue((result as CreationResult.Failure).errors.contains(ErrorType.INVALID_ROUTE_NUMBER))
    }

    @Test
    fun `Ticket creation with multiple errors`() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(ErrorType.INVALID_ROUTE_NUMBER)
        `when`(mockValidator.validateLicensePlate(anyString())).thenReturn(ErrorType.EMPTY_LICENSE_PLATE)

        val result = Ticket.create(mockValidator, "XYZ", "", emptyList())

        assertTrue(result is CreationResult.Failure)
        val errors = (result as CreationResult.Failure).errors
        assertTrue(errors.contains(ErrorType.INVALID_ROUTE_NUMBER))
        assertTrue(errors.contains(ErrorType.EMPTY_LICENSE_PLATE))
    }
}
