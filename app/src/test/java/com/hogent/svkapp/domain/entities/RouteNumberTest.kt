package com.hogent.svkapp.domain.entities

import org.junit.Assert.assertTrue
import org.junit.Test
import com.hogent.svkapp.util.CustomResult


class RouteNumberTest {

    private val someRouteNumber: String = "1400123456"
    private val emptyRouteNumber: String = ""
    private val nonPositiveRouteNumber: String = "-1400123456"
    private val invalidFormatRouteNumber: String = "1400-123abc"

    @Test
    fun routeNumber_Creation_Success() {
        assertTrue(RouteNumber.create(someRouteNumber) is CustomResult.Success<RouteNumber, List<RouteNumberError?>>)
    }

    @Test
    fun routeNumber_Creation_Empty() {
        assertTrue(RouteNumber.create(emptyRouteNumber) is CustomResult.Failure<RouteNumber, List<RouteNumberError?>>)
    }

    @Test
    fun routeNumber_Creation_NonPositive() {
        assertTrue(RouteNumber.create(nonPositiveRouteNumber) is CustomResult.Failure<RouteNumber,
                List<RouteNumberError?>>)
    }

    @Test
    fun routeNumber_Creation_InvalidFormat() {
        assertTrue(RouteNumber.create(invalidFormatRouteNumber) is CustomResult.Failure<RouteNumber,
                List<RouteNumberError?>>)
    }

    @Test
    fun routeNumber_Validation_Success() {
        assertTrue(RouteNumber.validateStringRepresentation(someRouteNumber).isEmpty())
    }

    @Test
    fun routeNumber_Validation_Empty() {
        assertTrue(RouteNumber.validateStringRepresentation(emptyRouteNumber).contains(RouteNumberError.EMPTY))
    }

    @Test
    fun routeNumber_Validation_NonPositive() {
        assertTrue(
            RouteNumber.validateStringRepresentation(nonPositiveRouteNumber)
                .contains(RouteNumberError.NON_POSITIVE_NUMBER)
        )
    }

    @Test
    fun routeNumber_Validation_InvalidFormat() {
        assertTrue(
            RouteNumber.validateStringRepresentation(invalidFormatRouteNumber).contains(RouteNumberError.INVALID_FORMAT)
        )
    }
}