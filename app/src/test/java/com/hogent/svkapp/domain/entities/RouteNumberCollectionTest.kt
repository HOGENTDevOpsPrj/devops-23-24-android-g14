package com.hogent.svkapp.domain.entities

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class RouteNumberCollectionTest {

    private val someRouteNumber: String = "1400123456"
    private val emptyRouteNumber: String = ""
    private val nonPositiveRouteNumber: String = "-1400123456"
    private val invalidFormatRouteNumber: String = "1400-123abc"

    private lateinit var routeNumber: RouteNumber

    @Before
    fun setUp() {
        routeNumber = mock()
    }

    @Test
    fun routeNumberCollection_Creation_Success() {
        assertTrue(RouteNumberCollection.create(listOf(routeNumber)) is Result.Success<RouteNumberCollection, RouteNumberCollectionError>)
    }

    @Test
    fun routeNumberCollection_Creation_Failure() {
        assertTrue(RouteNumberCollection.create(emptyList()) is Result.Failure<RouteNumberCollection, RouteNumberCollectionError>)
    }

    @Test
    fun routeNumberCollection_Validation_Success() {
        assertTrue(
            RouteNumberCollection.validateStringRepresentations(listOf(someRouteNumber)) is Result
            .Success<List<List<RouteNumberError?>>, RouteNumberCollectionError>
        )
    }

    @Test
    fun routeNumberCollection_Validation_EmptyRouteNumber() {
        assertTrue(
            RouteNumberCollection.validateStringRepresentations(listOf(emptyRouteNumber)) is Result
            .Success<List<List<RouteNumberError?>>, RouteNumberCollectionError>
        )
    }

    @Test
    fun routeNumberCollection_Validation_EmptyList() {
        assertTrue(
            RouteNumberCollection.validateStringRepresentations(emptyList()) is Result
            .Failure<List<List<RouteNumberError?>>, RouteNumberCollectionError>
        )
    }

    @Test
    fun routeNumberCollection_Validation_NonPositive() {
        assertTrue(
            RouteNumberCollection.validateStringRepresentations(listOf(nonPositiveRouteNumber)) is Result
            .Success<List<List<RouteNumberError?>>, RouteNumberCollectionError>
        )
    }

    @Test
    fun routeNumberCollection_Validation_InvalidFormat() {
        assertTrue(
            RouteNumberCollection.validateStringRepresentations(listOf(invalidFormatRouteNumber)) is Result
            .Success<List<List<RouteNumberError?>>, RouteNumberCollectionError>
        )
    }
}