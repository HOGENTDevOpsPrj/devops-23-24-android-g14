package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.util.CustomResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LicensePlateTest {
    @Test
    fun `create LicensePlate with valid plate should succeed`() {
        val plate = "ABC123"
        val result = LicensePlate.create(plate)

        assertTrue(result is CustomResult.Success)
        assertEquals(plate, (result as CustomResult.Success).value.value)
    }

    @Test
    fun `create LicensePlate with empty plate should fail`() {
        val plate = ""
        val result = LicensePlate.create(plate)

        assertTrue(result is CustomResult.Failure)
        assertEquals(LicensePlateError.EMPTY, (result as CustomResult.Failure).error)
    }

    @Test
    fun `create LicensePlate with too long plate should fail`() {
        val plate = "A".repeat(41) // Assuming 40 is the maximum length
        val result = LicensePlate.create(plate)

        assertTrue(result is CustomResult.Failure)
        assertEquals(LicensePlateError.TOO_LONG, (result as CustomResult.Failure).error)
    }

    @Test
    fun `validate with valid plate should return null`() {
        val plate = "ABC123"
        val result = LicensePlate.validate(plate)

        assertNull(result)
    }

    @Test
    fun `validate with empty plate should return EMPTY error`() {
        val plate = ""
        val result = LicensePlate.validate(plate)

        assertEquals(LicensePlateError.EMPTY, result)
    }

    @Test
    fun `validate with too long plate should return TOO_LONG error`() {
        val plate = "A".repeat(41) // Assuming 40 is the maximum length
        val result = LicensePlate.validate(plate)

        assertEquals(LicensePlateError.TOO_LONG, result)
    }
}
