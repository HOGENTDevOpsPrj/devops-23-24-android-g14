package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.util.CustomResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Auth0IdTest {

    @Test
    fun `create Auth0Id with valid non-empty value should succeed`() {
        val validValue = "someValidId123"
        val result = Auth0Id.create(validValue)

        assertTrue(result is CustomResult.Success)
        assertEquals(validValue, (result as CustomResult.Success).value.value)
    }

    @Test
    fun `create Auth0Id with empty value should return EMPTY error`() {
        val emptyValue = ""
        val result = Auth0Id.create(emptyValue)

        assertTrue(result is CustomResult.Failure)
        assertEquals(Auth0IdError.EMPTY, (result as CustomResult.Failure).error)
    }

    @Test
    fun `create Auth0Id with whitespace value should return EMPTY error`() {
        val whitespaceValue = "    "
        val result = Auth0Id.create(whitespaceValue)

        assertTrue(result is CustomResult.Failure)
        assertEquals(Auth0IdError.EMPTY, (result as CustomResult.Failure).error)
    }

    @Test
    fun `Auth0Id value should be trimmed`() {
        val untrimmedValue = "  validId123  "
        val expectedTrimmedValue = "validId123"
        val result = Auth0Id.create(untrimmedValue)

        assertTrue(result is CustomResult.Success)
        assertEquals(expectedTrimmedValue, (result as CustomResult.Success).value.value)
    }
}
