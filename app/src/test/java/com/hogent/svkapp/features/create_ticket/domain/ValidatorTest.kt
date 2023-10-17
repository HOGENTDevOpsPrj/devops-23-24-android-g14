package com.hogent.svkapp.features.create_ticket.domain

import com.hogent.svkapp.features.create_ticket.domain.entities.ErrorType
import com.hogent.svkapp.features.create_ticket.domain.entities.Image
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ValidatorTest {

    private lateinit var validator: Validator

    @Mock
    private lateinit var mockImage: Image

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        validator = Validator()
    }

    @Test
    fun `routeNumber empty validation`() {
        val error = validator.validateRouteNumber("   ")
        assertEquals(ErrorType.EMPTY_ROUTE, error)
    }

    @Test
    fun `routeNumber invalid format validation`() {
        val error = validator.validateRouteNumber("ABC")
        assertEquals(ErrorType.INVALID_ROUTE_NUMBER, error)
    }

    @Test
    fun `routeNumber less than or equal to zero validation`() {
        val error = validator.validateRouteNumber("0")
        assertEquals(ErrorType.INVALID_ROUTE_NUMBER, error)
    }

    @Test
    fun `routeNumber valid format`() {
        val error = validator.validateRouteNumber("123")
        assertNull(error)
    }

    @Test
    fun `licensePlate empty validation`() {
        val error = validator.validateLicensePlate("  ")
        assertEquals(ErrorType.EMPTY_LICENSE_PLATE, error)
    }

    @Test
    fun `licensePlate long length validation`() {
        val longLicensePlate = "A".repeat(MAX_LICENSE_PLATE_LENGTH + 1)
        val error = validator.validateLicensePlate(longLicensePlate)
        assertEquals(ErrorType.LONG_LICENSE_PLATE, error)
    }

    @Test
    fun `licensePlate valid format`() {
        val licensePlate = "AB-123-CD"
        val error = validator.validateLicensePlate(licensePlate)
        assertNull(error)
    }

    @Test
    fun `images empty validation`() {
        val error = validator.validateImages(emptyList())
        assertEquals(ErrorType.EMPTY_IMAGES, error)
    }

    @Test
    fun `images valid list`() {
        val images = listOf(mockImage, mockImage)
        val error = validator.validateImages(images)
        assertNull(error)
    }
}
