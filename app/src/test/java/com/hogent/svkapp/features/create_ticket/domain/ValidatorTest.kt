package com.hogent.svkapp.features.create_ticket.domain

import com.hogent.svkapp.features.create_ticket.domain.entities.ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidatorTest {
    private lateinit var validator: Validator

    @Before
    fun setUp() {
        validator = Validator()
    }

    @Test
    fun `validateLicensePlate for empty license plate`() {
        assertEquals(
            ValidationResult.Invalid(message = "Vul een nummerplaat in."),
            validator.validateLicensePlate("   ")
        )
    }

    @Test
    fun `validateLicensePlate for long license plate`() {
        val longLicensePlate = "A".repeat(41)
        assertEquals(
            ValidationResult.Invalid(message = "Nummerplaat is te lang."),
            validator.validateLicensePlate(longLicensePlate)
        )
    }

    @Test
    fun `validateLicensePlate for valid license plate`() {
        assertEquals(
            ValidationResult.Valid, validator.validateLicensePlate("AB-1234")
        )
    }

    @Test
    fun `validateRouteNumber for empty route number`() {
        assertEquals(
            ValidationResult.Invalid(message = "Vul een routenummer in."),
            validator.validateRouteNumber("   ")
        )
    }

    @Test
    fun `validateRouteNumber for non-integer route number`() {
        assertEquals(
            ValidationResult.Invalid(message = "Routenummer is ongeldig."),
            validator.validateRouteNumber("ABCD")
        )
    }

    @Test
    fun `validateRouteNumber for zero route number`() {
        assertEquals(
            ValidationResult.Invalid(message = "Routenummer is ongeldig."),
            validator.validateRouteNumber("0")
        )
    }

    @Test
    fun `validateRouteNumber for negative route number`() {
        assertEquals(
            ValidationResult.Invalid(message = "Routenummer is ongeldig."),
            validator.validateRouteNumber("-5")
        )
    }

    @Test
    fun `validateRouteNumber for valid route number`() {
        assertEquals(
            ValidationResult.Valid, validator.validateRouteNumber("5")
        )
    }

    @Test
    fun `sanitizeLicensePlate trims and uppercases`() {
        assertEquals(
            "ABC-1234", validator.sanitizeLicensePlate(" abc-1234  ")
        )
    }

    @Test
    fun `sanitizeRouteNumber trims and converts`() {
        assertEquals(
            5, validator.sanitizeRouteNumber(" 5 ")
        )
    }
}
