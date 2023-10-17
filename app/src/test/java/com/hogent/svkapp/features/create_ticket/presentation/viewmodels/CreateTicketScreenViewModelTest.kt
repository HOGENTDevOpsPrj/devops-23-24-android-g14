package com.hogent.svkapp.features.create_ticket.presentation.viewmodels

import com.hogent.svkapp.features.create_ticket.domain.TicketCreator
import com.hogent.svkapp.features.create_ticket.domain.Validator
import com.hogent.svkapp.features.create_ticket.domain.entities.ValidationResult
import com.hogent.svkapp.features.create_ticket.domain.entities.Image
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyList
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class CreateTicketScreenViewModelTest {
    private lateinit var viewModel: CreateTicketScreenViewModel
    private lateinit var mockValidator: Validator
    private lateinit var mockTicketCreator: TicketCreator

    @Before
    fun setUp() {
        mockValidator = mock()
        mockTicketCreator = mock()
        viewModel = CreateTicketScreenViewModel(
            validator = mockValidator, ticketCreator = mockTicketCreator
        )
    }

    @Test
    fun `onSend with valid data submits form`() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(ValidationResult.Valid)
        `when`(mockValidator.validateLicensePlate(anyString())).thenReturn(ValidationResult.Valid)
        `when`(mockValidator.sanitizeRouteNumber(anyString())).thenReturn(123)
        `when`(mockValidator.sanitizeLicensePlate(anyString())).thenReturn("1-ABC-123")
        `when`(mockValidator.validateImages(anyList())).thenReturn(ValidationResult.Valid)

        viewModel.onSend()

        verify(mockTicketCreator).createTicket(anyInt(), anyString(), anyList())
    }

    @Test
    fun `onSend with invalid data does not submit form`() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(ValidationResult.Invalid("Routenummer is ongeldig."))
        `when`(mockValidator.validateLicensePlate(anyString())).thenReturn(
            ValidationResult.Invalid(
                message = "Nummerplaat is ongeldig."
            )
        )
        `when`(mockValidator.validateImages(anyList())).thenReturn(ValidationResult.Invalid(message = "Voeg minstens één foto toe."))

        viewModel.onSend()

        verify(mockTicketCreator, never()).createTicket(anyInt(), anyString(), anyList())
    }

    @Test
    fun `onSend with valid data resets form and errors`() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(ValidationResult.Valid)
        `when`(mockValidator.validateLicensePlate(anyString())).thenReturn(ValidationResult.Valid)
        `when`(mockValidator.sanitizeRouteNumber(anyString())).thenReturn(123)
        `when`(mockValidator.sanitizeLicensePlate(anyString())).thenReturn("1-ABC-123")
        `when`(mockValidator.validateImages(anyList())).thenReturn(ValidationResult.Valid)

        viewModel.onRouteNumberChange(routeNumber = "123")
        viewModel.onLicensePlateChange(licensePlate = "1-ABC-123")
        viewModel.addImage(image = mock(Image::class.java))
        viewModel.onSend()

        assertEquals("", viewModel.routeNumber.value)
        assertEquals("", viewModel.licensePlate.value)
        assertEquals(null, viewModel.routeNumberError.value)
        assertEquals(null, viewModel.licensePlateError.value)
        assertEquals(0, viewModel.images.size)
    }

    @Test
    fun `onRouteNumberChange updates routeNumber`() {
        `when`(mockValidator.validateRouteNumber(routeNumber = anyString())).thenReturn(
            ValidationResult.Valid
        )
        viewModel.onRouteNumberChange(routeNumber = "123")

        assertEquals("123", viewModel.routeNumber.value)
    }

    @Test
    fun `onLicensePlateChange updates licensePlate`() {
        `when`(mockValidator.validateLicensePlate(licensePlate = anyString())).thenReturn(
            ValidationResult.Valid
        )
        viewModel.onLicensePlateChange(licensePlate = "1-ABC-123")

        assertEquals("1-ABC-123", viewModel.licensePlate.value)
    }

    @Test
    fun `onLicensePlateChange will capitalize licensePlate`() {
        `when`(mockValidator.validateLicensePlate(licensePlate = anyString())).thenReturn(
            ValidationResult.Valid
        )
        viewModel.onLicensePlateChange(licensePlate = "1-abc-123")

        assertEquals("1-ABC-123", viewModel.licensePlate.value)
    }

    @Test
    fun `validateRouteNumber with valid route number sets no error`() {
        `when`(mockValidator.validateRouteNumber(routeNumber = anyString())).thenReturn(
            ValidationResult.Valid
        )

        viewModel.onRouteNumberChange(routeNumber = "123")

        assertEquals(null, viewModel.routeNumberError.value)
    }

    @Test
    fun `validateRouteNumber with invalid route number sets error`() {
        `when`(mockValidator.validateRouteNumber(routeNumber = anyString())).thenReturn(
            ValidationResult.Invalid("Routenummer is ongeldig.")
        )

        viewModel.onRouteNumberChange(routeNumber = "123")

        assertEquals("Routenummer is ongeldig.", viewModel.routeNumberError.value)
    }

    @Test
    fun `validateLicensePlate with valid license plate sets no error`() {
        `when`(mockValidator.validateLicensePlate(licensePlate = anyString())).thenReturn(
            ValidationResult.Valid
        )

        viewModel.onLicensePlateChange(licensePlate = "1-ABC-123")

        assertEquals(null, viewModel.licensePlateError.value)
    }

    @Test
    fun `validateLicensePlate with invalid license plate sets error`() {
        `when`(mockValidator.validateLicensePlate(licensePlate = anyString())).thenReturn(
            ValidationResult.Invalid(
                message = "Nummerplaat is ongeldig."
            )
        )

        viewModel.onLicensePlateChange(licensePlate = "1-ABC-123")

        assertEquals("Nummerplaat is ongeldig.", viewModel.licensePlateError.value)
    }

    @Test
    fun `addImage adds image to images`() {
        val image = mock(Image::class.java)
        `when`(mockValidator.validateImages(images = anyList())).thenReturn(ValidationResult.Valid)
        viewModel.addImage(image = image)

        assertEquals(1, viewModel.images.size)
        assertEquals(image, viewModel.images[0])
    }

    @Test
    fun `addImage validates images`() {
        val image = mock(Image::class.java)
        `when`(mockValidator.validateImages(images = anyList())).thenReturn(ValidationResult.Valid)
        viewModel.addImage(image)

        verify(mockValidator).validateImages(images = anyList())
    }

    @Test
    fun `addImage with valid images sets no error`() {
        val image = mock(Image::class.java)
        `when`(mockValidator.validateImages(images = anyList())).thenReturn(ValidationResult.Valid)
        viewModel.addImage(image = image)

        assertEquals(null, viewModel.imagesError.value)
    }

    @Test
    fun `addImage with invalid images sets error`() {
        val image = mock(Image::class.java)
        `when`(mockValidator.validateImages(images = anyList())).thenReturn(
            ValidationResult.Invalid(
                "Voeg minstens één foto toe."
            )
        )
        viewModel.addImage(image = image)

        assertEquals("Voeg minstens één foto toe.", viewModel.imagesError.value)
    }
}
