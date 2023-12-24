package com.hogent.svkapp.domain.entities

import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import com.hogent.svkapp.util.CustomResult


class LicensePlateTest {
    private val somePlate = "1-ABC-123"
    private val emptyPlate = ""
    private val tooLongPlate = "This is a string with more than 40 characters. It should be long enough for your needs."

    @Test
    fun licensePlate_Creation_Success() {
        assertTrue(LicensePlate.create(somePlate) is CustomResult.Success<LicensePlate, LicensePlateError>)
    }

    @Test
    fun licensePlate_Creation_Empty() {
        assertTrue(LicensePlate.create(emptyPlate) is CustomResult.Failure<LicensePlate, LicensePlateError>)
    }

    @Test
    fun licensePlate_Creation_TooLong() {
        assertTrue(LicensePlate.create(tooLongPlate) is CustomResult.Failure<LicensePlate, LicensePlateError>)
    }

    @Test
    fun licensePlate_Validation_Success() {
        assertNull(LicensePlate.validate(somePlate))
    }

    @Test
    fun licensePlate_Validation_Empty() {
        assertTrue(LicensePlate.validate(emptyPlate) == LicensePlateError.EMPTY)
    }

    @Test
    fun licensePlate_Validation_TooLong() {
        assertTrue(LicensePlate.validate(tooLongPlate) == LicensePlateError.TOO_LONG)
    }
}