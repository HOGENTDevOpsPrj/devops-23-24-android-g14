package com.hogent.svkapp.features.create_ticket.presentation.viewmodels

import com.hogent.svkapp.features.create_ticket.domain.TicketCreator
import com.hogent.svkapp.features.create_ticket.domain.Validator
import com.hogent.svkapp.features.create_ticket.domain.entities.Image
import com.hogent.svkapp.features.create_ticket.domain.entities.ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyList
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

const val ROUTE_NUMBER_ERROR = "Routenummer is ongeldig."
const val LICENSE_PLATE_ERROR = "Nummerplaat is ongeldig."
const val IMAGES_ERROR = "Voeg minstens één foto toe."

class CreateTicketScreenViewModelTest {
    @Mock
    private lateinit var mockValidator: Validator

    @Mock
    private lateinit var mockTicketCreator: TicketCreator

    private lateinit var viewModel: CreateTicketScreenViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CreateTicketScreenViewModel(
            validator = mockValidator, ticketCreator = mockTicketCreator
        )
    }

    private fun setupValidValidation() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(ValidationResult.Valid)
        `when`(mockValidator.validateLicensePlate(anyString())).thenReturn(ValidationResult.Valid)
        `when`(mockValidator.validateImages(anyList())).thenReturn(ValidationResult.Valid)
    }

    private fun setupInvalidValidation() {
        `when`(mockValidator.validateRouteNumber(anyString())).thenReturn(
            ValidationResult.Invalid(
                ROUTE_NUMBER_ERROR
            )
        )
        `when`(mockValidator.validateLicensePlate(anyString())).thenReturn(
            ValidationResult.Invalid(
                LICENSE_PLATE_ERROR
            )
        )
        `when`(mockValidator.validateImages(anyList())).thenReturn(
            ValidationResult.Invalid(
                IMAGES_ERROR
            )
        )
    }

    @Test
    fun `onSend with valid data submits form`() {
        setupValidValidation()

        `when`(mockValidator.sanitizeRouteNumber(anyString())).thenReturn(
            123
        )
        `when`(mockValidator.sanitizeLicensePlate(anyString())).thenReturn(
            "1-ABC-123"
        )

        viewModel.onSend()

        verify(mockTicketCreator).createTicket(anyInt(), anyString(), anyList())
    }

    @Test
    fun `onSend with invalid data does not submit form`() {
        setupInvalidValidation()

        viewModel.onSend()

        verify(mockTicketCreator, never()).createTicket(anyInt(), anyString(), anyList())
    }

    @Test
    fun `onSend with valid data resets form and errors`() {
        setupValidValidation()

        viewModel.onRouteNumberChange(routeNumber = "routeNumberTest")
        viewModel.onLicensePlateChange(licensePlate = "licensePlateTest")
        viewModel.addImage(image = mock(Image::class.java))
        viewModel.onSend()

        assertEquals("", viewModel.routeNumber.value)
        assertEquals("", viewModel.licensePlate.value)
        assertEquals(null, viewModel.routeNumberError.value)
        assertEquals(null, viewModel.licensePlateError.value)
        assertEquals(0, viewModel.images.size)
    }

    @Test
    fun `onSend with invalid data does not reset form and errors`() {
        setupInvalidValidation()

        viewModel.onRouteNumberChange(routeNumber = "routeNumberTest")
        viewModel.onLicensePlateChange(licensePlate = "licensePlateTest")
        viewModel.addImage(image = mock(Image::class.java))
        viewModel.onSend()

        assertEquals("routeNumberTest", viewModel.routeNumber.value)
        assertEquals("LICENSEPLATETEST", viewModel.licensePlate.value)
        assertEquals(ROUTE_NUMBER_ERROR, viewModel.routeNumberError.value)
        assertEquals(LICENSE_PLATE_ERROR, viewModel.licensePlateError.value)
        assertEquals(1, viewModel.images.size)
    }

    @Test
    fun `onRouteNumberChange updates routeNumber`() {
        viewModel.onRouteNumberChange(routeNumber = "123")
        assertEquals("123", viewModel.routeNumber.value)

        viewModel.onRouteNumberChange(routeNumber = "")
        assertEquals("", viewModel.routeNumber.value)

        viewModel.onRouteNumberChange(routeNumber = "invalid")
        assertEquals("invalid", viewModel.routeNumber.value)
    }

    @Test
    fun `onLicensePlateChange updates licensePlate`() {
        viewModel.onLicensePlateChange(licensePlate = "1-ABC-123")
        assertEquals("1-ABC-123", viewModel.licensePlate.value)

        viewModel.onLicensePlateChange(licensePlate = "")
        assertEquals("", viewModel.licensePlate.value)

        val tooLong = "12345".repeat(10)
        viewModel.onLicensePlateChange(licensePlate = tooLong)
        assertEquals(tooLong, viewModel.licensePlate.value)
    }

    @Test
    fun `onLicensePlateChange will capitalize licensePlate`() {
        viewModel.onLicensePlateChange(licensePlate = "test test")

        assertEquals("TEST TEST", viewModel.licensePlate.value)
    }

    @Test
    fun `validateRouteNumber with valid route number sets no error`() {
        setupValidValidation()
        viewModel.onRouteNumberChange(routeNumber = "")

        assertEquals(null, viewModel.routeNumberError.value)
    }

    @Test
    fun `validateRouteNumber with invalid route number sets error`() {
        setupInvalidValidation()
        viewModel.onRouteNumberChange(routeNumber = "")

        assertEquals(ROUTE_NUMBER_ERROR, viewModel.routeNumberError.value)
    }

    @Test
    fun `validateLicensePlate with valid license plate sets no error`() {
        setupValidValidation()
        viewModel.onLicensePlateChange(licensePlate = "")

        assertEquals(null, viewModel.licensePlateError.value)
    }

    @Test
    fun `validateLicensePlate with invalid license plate sets error`() {
        setupInvalidValidation()
        viewModel.onLicensePlateChange(licensePlate = "")

        assertEquals(LICENSE_PLATE_ERROR, viewModel.licensePlateError.value)
    }

    @Test
    fun `addImage adds image to images`() {
        val image = mock(Image::class.java)
        viewModel.addImage(image = image)

        assertEquals(1, viewModel.images.size)
        assertEquals(image, viewModel.images[0])
    }

    @Test
    fun `addImage with valid images sets no error`() {
        setupValidValidation()
        val image = mock(Image::class.java)
        viewModel.addImage(image = image)

        assertEquals(null, viewModel.imagesError.value)
    }

    @Test
    fun `addImage with invalid images sets error`() {
        setupInvalidValidation()
        val image = mock(Image::class.java)
        viewModel.addImage(image = image)

        assertEquals(IMAGES_ERROR, viewModel.imagesError.value)
    }
}
