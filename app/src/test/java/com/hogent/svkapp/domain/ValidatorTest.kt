package com.hogent.svkapp.domain

import com.hogent.svkapp.domain.entities.Image
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock

class ValidatorTest {
    private val validator = Validator()

    @Test
    fun `validateRouteNumber returns correct error when routeNumber is empty`() {
        val result = validator.validateRouteNumber(" ")
        assertEquals(ValidationError.EMPTY_ROUTE, (result as ValidationResult.Error).error)
    }

    @Test
    fun `validateRouteNumber returns correct error when routeNumber is not a number`() {
        val result = validator.validateRouteNumber("abc")
        assertEquals(ValidationError.INVALID_ROUTE_NUMBER, (result as ValidationResult.Error).error)
    }

    @Test
    fun `validateRouteNumber returns correct error when routeNumber is negative`() {
        val result = validator.validateRouteNumber("-1")
        assertEquals(ValidationError.INVALID_ROUTE_NUMBER, (result as ValidationResult.Error).error)
    }

    @Test
    fun `validateRouteNumber accepts positive integer`() {
        val result = validator.validateRouteNumber("1")
        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun `validateLicensePlate returns correct error when licensePlate is empty`() {
        val result = validator.validateLicensePlate(" ")
        assertEquals(ValidationError.EMPTY_LICENSE_PLATE, (result as ValidationResult.Error).error)
    }

    @Test
    fun `validateLicensePlate returns correct error when licensePlate is too long`() {
        val input = "A".repeat(MAX_LICENSE_PLATE_LENGTH + 1)
        val result = validator.validateLicensePlate(input)
        assertEquals(ValidationError.LONG_LICENSE_PLATE, (result as ValidationResult.Error).error)
    }

    @Test
    fun `validateLicensePlate accepts correct license plates`() {
        val result = validator.validateLicensePlate("ABC123")
        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun `validateImages returns correct error when list is empty`() {
        val images = emptyList<Image>()
        val result = validator.validateImages(images)
        assertEquals(ValidationError.EMPTY_IMAGES, (result as ValidationResult.Error).error)
    }

    @Test
    fun `validateImages allows any non-empty lists`() {
        val image: Image = mock()
        val images = listOf(image)

        val result = validator.validateImages(images)
        assertEquals(ValidationResult.Success, result)
    }
}
