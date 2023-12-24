package com.hogent.svkapp.domain.entities

import com.hogent.svkapp.util.CustomResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RouteNumberCollectionTest {

    @Test
    fun `create RouteNumberCollection with non-empty list should succeed`() {
        val routeNumbers = listOf(
            (RouteNumber.create("12345") as CustomResult.Success).value,
            (RouteNumber.create("67890") as CustomResult.Success).value
        )
        val result = RouteNumberCollection.create(routeNumbers)

        assertTrue(result is CustomResult.Success)
        assertEquals(routeNumbers, (result as CustomResult.Success).value.value)
    }

    @Test
    fun `create RouteNumberCollection with empty list should fail`() {
        val routeNumbers = emptyList<RouteNumber>()
        val result = RouteNumberCollection.create(routeNumbers)

        assertTrue(result is CustomResult.Failure)
        assertEquals(RouteNumberCollectionError.EMPTY, (result as CustomResult.Failure).error)
    }

    @Test
    fun `validateStringRepresentations with non-empty list should return success`() {
        val routeNumberStrings = listOf("12345", "67890")
        val result = RouteNumberCollection.validateStringRepresentations(routeNumberStrings)

        assertTrue(result is CustomResult.Success)
    }

    @Test
    fun `validateStringRepresentations with empty list should return failure`() {
        val routeNumberStrings = emptyList<String>()
        val result = RouteNumberCollection.validateStringRepresentations(routeNumberStrings)

        assertTrue(result is CustomResult.Failure)
        assertEquals(RouteNumberCollectionError.EMPTY, (result as CustomResult.Failure).error)
    }
}
