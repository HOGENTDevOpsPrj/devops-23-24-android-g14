package com.hogent.svkapp.presentation.viewmodels

import androidx.navigation.NavHostController
import com.hogent.svkapp.Route
import com.hogent.svkapp.data.repositories.CargoTicketRepository
import com.hogent.svkapp.domain.ValidationError
import com.hogent.svkapp.domain.ValidationResult
import com.hogent.svkapp.domain.Validator
import com.hogent.svkapp.domain.entities.Image
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MainScreenViewModelTest {

    private lateinit var validator: Validator
    private lateinit var cargoTicketRepository: CargoTicketRepository
    private lateinit var navController: NavHostController
    private lateinit var viewModel: MainScreenViewModel

    @Before
    fun setUp() {
        validator = mock()
        cargoTicketRepository = mock()
        navController = mock()
        viewModel = MainScreenViewModel(validator, cargoTicketRepository, navController)
    }

    @Test
    fun `when toggleDialog is called, showDialog should be toggled`() {
        viewModel.toggleDialog()

        assertTrue(viewModel.showDialog.value)
    }

    @Test
    fun `when addImage is called, image should be added`() {
        val image: Image = mock()

        viewModel.addImage(image)

        assertEquals(viewModel.images[0], image)
    }

    @Test
    fun `when addImage is called and it makes images be invalid, imagesError should be set`() {
        val image: Image = mock()
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Error(ValidationError.EMPTY_IMAGES))

        viewModel.addImage(image)

        assertEquals(ValidationError.EMPTY_IMAGES.message, viewModel.imagesError.value)
    }

    @Test
    fun `when addImage is called and it makes images be valid, imagesError should be set to null`() {
        val image: Image = mock()
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Success)

        viewModel.addImage(image)

        assertNull(viewModel.imagesError.value)
    }

    @Test
    fun `when deleteImage is called, image should be deleted`() {
        val image: Image = mock()
        viewModel.addImage(image)

        viewModel.deleteImage(image)

        assertTrue(viewModel.images.isEmpty())
    }

    @Test
    fun `when deleteImage is called and it makes images be invalid, imagesError should be set`() {
        val image: Image = mock()
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Error(ValidationError.EMPTY_IMAGES))

        viewModel.addImage(image)
        viewModel.deleteImage(image)

        assertEquals(ValidationError.EMPTY_IMAGES.message, viewModel.imagesError.value)
    }

    @Test
    fun `when deleteImage is called and it makes images be valid, imagesError should be set to null`() {
        val image: Image = mock()
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Success)

        viewModel.addImage(image)
        viewModel.deleteImage(image)

        assertNull(viewModel.imagesError.value)
    }

    @Test
    fun `when onSend is called and there is valid input, ticket should be added to repository`() {
        val image: Image = mock()
        viewModel.onRouteNumberChange("123")
        viewModel.onLicensePlateChange("ABC-123")
        viewModel.addImage(image)

        whenever(validator.validateRouteNumber(any())).thenReturn(ValidationResult.Success)
        whenever(validator.validateLicensePlate(any())).thenReturn(ValidationResult.Success)
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Success)

        viewModel.onSend()

        verify(cargoTicketRepository).addCargoTicket(any())
    }

    @Test
    fun `when onSend is called and there is valid input, form should be reset`() {
        val image: Image = mock()
        viewModel.onRouteNumberChange("123")
        viewModel.onLicensePlateChange("ABC-123")
        viewModel.addImage(image)

        whenever(validator.validateRouteNumber(any())).thenReturn(ValidationResult.Success)
        whenever(validator.validateLicensePlate(any())).thenReturn(ValidationResult.Success)
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Success)

        viewModel.onSend()

        assertEquals("", viewModel.routeNumber.value)
        assertEquals("", viewModel.licensePlate.value)
        assertTrue(viewModel.images.isEmpty())

        assertNull(viewModel.routeNumberError.value)
        assertNull(viewModel.licensePlateError.value)
        assertNull(viewModel.imagesError.value)
    }

    @Test
    fun `when onSend is called and there is valid input, dialog should be toggled`() {
        val image: Image = mock()
        viewModel.onRouteNumberChange("123")
        viewModel.onLicensePlateChange("ABC-123")
        viewModel.addImage(image)

        whenever(validator.validateRouteNumber(any())).thenReturn(ValidationResult.Success)
        whenever(validator.validateLicensePlate(any())).thenReturn(ValidationResult.Success)
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Success)

        viewModel.onSend()

        assertTrue(viewModel.showDialog.value)
    }

    @Test
    fun `when onSend is called and there is invalid input, ticket should not be added to repository`() {
        val image: Image = mock()
        viewModel.onRouteNumberChange("123")
        viewModel.onLicensePlateChange("ABC-123")
        viewModel.addImage(image)

        whenever(validator.validateRouteNumber(any())).thenReturn(
            ValidationResult.Error(
                ValidationError.EMPTY_ROUTE
            )
        )
        whenever(validator.validateLicensePlate(any())).thenReturn(
            ValidationResult.Error(
                ValidationError.EMPTY_LICENSE_PLATE
            )
        )
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Error(ValidationError.EMPTY_IMAGES))

        viewModel.onSend()

        verify(cargoTicketRepository, never()).addCargoTicket(any())
    }

    @Test
    fun `when onSend is called and there is invalid input, form should not be reset`() {
        val image: Image = mock()
        viewModel.onRouteNumberChange("123")
        viewModel.onLicensePlateChange("ABC-123")
        viewModel.addImage(image)

        whenever(validator.validateRouteNumber(any())).thenReturn(
            ValidationResult.Error(
                ValidationError.EMPTY_ROUTE
            )
        )
        whenever(validator.validateLicensePlate(any())).thenReturn(
            ValidationResult.Error(
                ValidationError.EMPTY_LICENSE_PLATE
            )
        )
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Error(ValidationError.EMPTY_IMAGES))

        viewModel.onSend()

        assertEquals("123", viewModel.routeNumber.value)
        assertEquals("ABC-123", viewModel.licensePlate.value)
        assertEquals(1, viewModel.images.size)
    }

    @Test
    fun `when onSend is called and there is invalid input, dialog should not be toggled`() {
        val image: Image = mock()
        viewModel.onRouteNumberChange("123")
        viewModel.onLicensePlateChange("ABC-123")
        viewModel.addImage(image)

        whenever(validator.validateRouteNumber(any())).thenReturn(
            ValidationResult.Error(
                ValidationError.EMPTY_ROUTE
            )
        )
        whenever(validator.validateLicensePlate(any())).thenReturn(
            ValidationResult.Error(
                ValidationError.EMPTY_LICENSE_PLATE
            )
        )
        whenever(validator.validateImages(any())).thenReturn(ValidationResult.Error(ValidationError.EMPTY_IMAGES))

        viewModel.onSend()

        assertFalse(viewModel.showDialog.value)
    }

    @Test
    fun `when onRouteNumberChange is called, routeNumber should be set`() {
        viewModel.onRouteNumberChange("123")

        assertEquals("123", viewModel.routeNumber.value)
    }

    @Test
    fun `when onRouteNumberChange is called with invalid input, routeNumberError should be set`() {
        whenever(validator.validateRouteNumber(any())).thenReturn(
            ValidationResult.Error(
                ValidationError.EMPTY_ROUTE
            )
        )

        viewModel.onRouteNumberChange("123")

        assertEquals(ValidationError.EMPTY_ROUTE.message, viewModel.routeNumberError.value)
    }

    @Test
    fun `when onRouteNumberChange is called with valid input, routeNumberError should be set to null`() {
        whenever(validator.validateRouteNumber(any())).thenReturn(ValidationResult.Success)

        viewModel.onRouteNumberChange("123")

        assertNull(viewModel.routeNumberError.value)
    }

    @Test
    fun `when onLicensePlateChange is called, licensePlate should be set`() {
        viewModel.onLicensePlateChange("ABC-123")

        assertEquals("ABC-123", viewModel.licensePlate.value)
    }

    @Test
    fun `when onLicensePlateChange is called with invalid input, licensePlateError should be set`() {
        whenever(validator.validateLicensePlate(any())).thenReturn(
            ValidationResult.Error(
                ValidationError.EMPTY_LICENSE_PLATE
            )
        )

        viewModel.onLicensePlateChange("ABC-123")

        assertEquals(ValidationError.EMPTY_LICENSE_PLATE.message, viewModel.licensePlateError.value)
    }

    @Test
    fun `when onLicensePlateChange is called with valid input, licensePlateError should be set to null`() {
        whenever(validator.validateLicensePlate(any())).thenReturn(ValidationResult.Success)

        viewModel.onLicensePlateChange("ABC-123")

        assertNull(viewModel.licensePlateError.value)
    }

    @Test
    fun `when onLogout is called, navController should navigate to Login`() {
        viewModel.onLogout()

        verify(navController).navigate(Route.Login.name)
    }
}
