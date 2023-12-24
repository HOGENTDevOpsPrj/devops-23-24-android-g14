package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.util.CustomResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LoadReceiptNumberTest {

    @Test
    fun `create LoadReceiptNumber with valid value should succeed`() {
        val value = "1004123456"
        val result = LoadReceiptNumber.create(value)

        assertTrue(result is CustomResult.Success)
        assertEquals(value, (result as CustomResult.Success).value.value)
    }

    @Test
    fun `create LoadReceiptNumber with empty value should fail`() {
        val value = ""
        val result = LoadReceiptNumber.create(value)

        assertTrue(result is CustomResult.Failure)
        assertEquals(LoadReceiptNumberError.EMPTY, (result as CustomResult.Failure).error)
    }

    @Test
    fun `create LoadReceiptNumber with invalid value should fail`() {
        val value = "1234567890"
        val result = LoadReceiptNumber.create(value)

        assertTrue(result is CustomResult.Failure)
        assertEquals(LoadReceiptNumberError.INVALID, (result as CustomResult.Failure).error)
    }

    @Test
    fun `validate with valid value should return null`() {
        val value = "1004123456"
        val result = LoadReceiptNumber.validate(value)

        assertNull(result)
    }

    @Test
    fun `validate with empty value should return EMPTY error`() {
        val value = ""
        val result = LoadReceiptNumber.validate(value)

        assertEquals(LoadReceiptNumberError.EMPTY, result)
    }

    @Test
    fun `validate with invalid value should return INVALID error`() {
        val value = "1234567890"
        val result = LoadReceiptNumber.validate(value)

        assertEquals(LoadReceiptNumberError.INVALID, result)
    }
}
