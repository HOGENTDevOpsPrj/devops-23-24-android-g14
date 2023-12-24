package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.util.CustomResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RouteNumberTest {

    @Test
    fun `create RouteNumber with valid value should succeed`() {
        val routeNumber = "123"
        val result = RouteNumber.create(routeNumber)

        assertTrue(result is CustomResult.Success)
        assertEquals(routeNumber, (result as CustomResult.Success).value.value)
    }

    @Test
    fun `create RouteNumber with empty value should fail with EMPTY error`() {
        val routeNumber = ""
        val result = RouteNumber.create(routeNumber)

        assertTrue(result is CustomResult.Failure)
        assertTrue((result as CustomResult.Failure).error.contains(RouteNumberError.EMPTY))
    }

    @Test
    fun `create RouteNumber with non-positive value should fail with NON_POSITIVE_NUMBER error`() {
        val routeNumber = "0"
        val result = RouteNumber.create(routeNumber)

        assertTrue(result is CustomResult.Failure)
        assertTrue((result as CustomResult.Failure).error.contains(RouteNumberError.NON_POSITIVE_NUMBER))
    }

    @Test
    fun `create RouteNumber with invalid format should fail with INVALID_FORMAT error`() {
        val routeNumber = "abc"
        val result = RouteNumber.create(routeNumber)

        assertTrue(result is CustomResult.Failure)
        assertTrue((result as CustomResult.Failure).error.contains(RouteNumberError.INVALID_FORMAT))
    }

    @Test
    fun `validateStringRepresentation with valid value should return empty list`() {
        val routeNumber = "123"
        val result = RouteNumber.validateStringRepresentation(routeNumber)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `validateStringRepresentation with empty value should return list with EMPTY error`() {
        val routeNumber = ""
        val result = RouteNumber.validateStringRepresentation(routeNumber)

        assertEquals(listOf(RouteNumberError.EMPTY), result)
    }

    @Test
    fun `validateStringRepresentation with non-positive value should return list with NON_POSITIVE_NUMBER error`() {
        val routeNumber = "0"
        val result = RouteNumber.validateStringRepresentation(routeNumber)

        assertEquals(listOf(RouteNumberError.NON_POSITIVE_NUMBER), result)
    }

    @Test
    fun `validateStringRepresentation with invalid format should return list with INVALID_FORMAT error`() {
        val routeNumber = "abc"
        val result = RouteNumber.validateStringRepresentation(routeNumber)

        assertEquals(listOf(RouteNumberError.INVALID_FORMAT), result)
    }
}
