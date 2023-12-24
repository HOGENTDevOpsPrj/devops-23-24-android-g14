package com.hogent.svkapp.presentation.viewmodels
//
import android.util.Log
import com.hogent.svkapp.data.repositories.CargoTicketLocalRepository
import com.hogent.svkapp.data.repositories.MainCargoTicketRepository
import com.hogent.svkapp.data.repositories.UserApiRepository
import com.hogent.svkapp.domain.entities.Image
import com.hogent.svkapp.domain.entities.ImageCollection
import com.hogent.svkapp.domain.entities.ImageCollectionError
import com.hogent.svkapp.domain.entities.LicensePlate
import com.hogent.svkapp.domain.entities.LicensePlateError
import com.hogent.svkapp.domain.entities.RouteNumberCollection
import com.hogent.svkapp.domain.entities.RouteNumberCollectionError
import com.hogent.svkapp.util.CustomResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MainScreenViewModelTest {

    @Mock
    private lateinit var mockCargoTicketRepository: MainCargoTicketRepository

    @Mock
    private lateinit var mockUserApiRepository: UserApiRepository

    private lateinit var viewModel: MainScreenViewModel

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun setUp() {
        mockCargoTicketRepository = mock<MainCargoTicketRepository> {
            onBlocking { addCargoTicket(any()) } doReturn Unit
            on { getNonProcessedCargoTickets() } doReturn emptyList()
        }
        mockUserApiRepository = mock<UserApiRepository> {
            onBlocking { addUser(any()) } doReturn CustomResult.Success(Unit)
        }
        viewModel = MainScreenViewModel(mockCargoTicketRepository, mockUserApiRepository)
    }

    @Test
    fun `when toggleDialog is called, showDialog should be toggled`() {


        viewModel.toggleDialog()

        assertTrue(viewModel.uiState.value.showPopup)
    }

    @Test
    fun `when addImage is called, image should be added`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }


        val image: Image = mock()

        viewModel.addImage(image)

        assertEquals(viewModel.uiState.value.imageCollection[0], image)
    }

    @Test
    fun `when addImage is called and it makes images be invalid, imagesError should be set`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        val image: Image = mock()

        whenever(ImageCollection.validate(any())).thenReturn(ImageCollectionError.EMPTY)

        viewModel.addImage(image)
        viewModel.removeImage(image)

        assertEquals(ImageCollectionError.EMPTY, viewModel.uiState.value.imageCollectionError)
    }

    @Test
    fun `when addImage is called and it makes images be valid, imagesError should be set to null`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        val image: Image = mock()

        viewModel.addImage(image)

        assertNull(viewModel.uiState.value.imageCollectionError)
    }

    @Test
    fun `when deleteImage is called, image should be deleted`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        val image: Image = mock()
        viewModel.addImage(image)

        viewModel.removeImage(image)

        assertTrue(viewModel.uiState.value.imageCollection.isEmpty())
    }

    @Test
    fun `when deleteImage is called and it makes images be valid, imagesError should be set to null`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        val image: Image = mock()
        val image2: Image = mock()

        viewModel.addImage(image)
        viewModel.addImage(image2)
        viewModel.removeImage(image)

        assertNull(viewModel.uiState.value.imageCollectionError)
    }

    @Test
    fun `when onSend is called and there is valid input, form should be reset`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        val image: Image = mock()
        viewModel.onRouteNumberChange(0, "123")
        viewModel.onLicensePlateChange("ABC-123")
        viewModel.addImage(image)

        viewModel.onSend()

        assertTrue(viewModel.uiState.value.routeNumberInputFieldValues.isEmpty())
        assertEquals("", viewModel.uiState.value.licensePlateInputFieldValue)
        assertTrue(viewModel.uiState.value.imageCollection.isEmpty())

        assertTrue(viewModel.uiState.value.routeNumberInputFieldValidationErrors.isEmpty())
        assertNull(viewModel.uiState.value.licensePlateInputFieldValidationError)
        assertNull(viewModel.uiState.value.imageCollectionError)
    }

    @Test
    fun `when onSend is called and there is valid input, dialog should be toggled`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        val image: Image = mock()
        viewModel.onRouteNumberChange(0, "123")
        viewModel.onLicensePlateChange("ABC-123")
        viewModel.addImage(image)

        viewModel.onSend()

        assertTrue(viewModel.uiState.value.showPopup)
    }

//    @Test
//    suspend fun `when onSend is called and there is invalid input, ticket should not be added to repository`() {
//        val image: Image = mock()
//        viewModel.onRouteNumberChange(0, "123")
//        viewModel.onLicensePlateChange("ABC-123")
//        viewModel.addImage(image)
//
//        whenever(ImageCollection.validate(any())).thenReturn(ImageCollectionError.Empty)
//
//        viewModel.onSend()
//
//        verify(cargoTicketRepository, never()).addCargoTicket(any())
//    }

    @Test
    fun `when onSend is called and there is invalid input, form should not be reset`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        val image: Image = mock()
        viewModel.onRouteNumberChange(0, "123")
        viewModel.onLicensePlateChange("ABC-123")
        viewModel.addImage(image)

        whenever(RouteNumberCollection.validateStringRepresentations(any())).thenReturn(
            CustomResult.Failure(RouteNumberCollectionError.EMPTY)
        )
        whenever(LicensePlate.validate(any())).thenReturn(
            LicensePlateError.EMPTY
        )
        whenever(ImageCollection.validate(any())).thenReturn(ImageCollectionError.EMPTY)

        viewModel.onSend()

        assertEquals("123", viewModel.uiState.value.routeNumberInputFieldValues[0])
        assertEquals("ABC-123", viewModel.uiState.value.licensePlateInputFieldValue)
        assertEquals(1, viewModel.uiState.value.imageCollection.size)
    }

    @Test
    fun `when onSend is called and there is invalid input, dialog should not be toggled`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        val image: Image = mock()
        viewModel.onRouteNumberChange(0, "123")
        viewModel.onLicensePlateChange("ABC-123")
        viewModel.addImage(image)

        whenever(RouteNumberCollection.validateStringRepresentations(any())).thenReturn(
            CustomResult.Failure(RouteNumberCollectionError.EMPTY)
        )
        whenever(LicensePlate.validate(any())).thenReturn(
            LicensePlateError.EMPTY
        )
        whenever(ImageCollection.validate(any())).thenReturn(ImageCollectionError.EMPTY)

        viewModel.onSend()

        assertFalse(viewModel.uiState.value.showPopup)
    }

    @Test
    fun `when onRouteNumberChange is called, routeNumber should be set`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }


        viewModel.onRouteNumberChange(0, "123")

        assertEquals("123", viewModel.uiState.value.routeNumberInputFieldValues[0])
    }

    @Test
    fun `when onRouteNumberChange is called with invalid input, routeNumberError should be set`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        whenever(RouteNumberCollection.validateStringRepresentations(any())).thenReturn(
            CustomResult.Failure(RouteNumberCollectionError.EMPTY)
        )

        viewModel.onRouteNumberChange(0, "123")

        assertEquals(RouteNumberCollectionError.EMPTY, viewModel.uiState.value.routeNumberCollectionError)
    }

    @Test
    fun `when onRouteNumberChange is called with valid input, routeNumberError should be set to null`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        whenever(RouteNumberCollection.validateStringRepresentations(any())).thenReturn(
            CustomResult.Success(
                listOf(
                    listOf
                        (null)
                )
            )
        )

        viewModel.onRouteNumberChange(0, "123")

        assertNull(viewModel.uiState.value.routeNumberCollectionError)
    }

    @Test
    fun `when onLicensePlateChange is called, licensePlate should be set`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        viewModel.onLicensePlateChange("ABC-123")

        assertEquals("ABC-123", viewModel.uiState.value.licensePlateInputFieldValue)
    }

    @Test
    fun `when onLicensePlateChange is called with invalid input, licensePlateError should be set`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        whenever(LicensePlate.validate(any())).thenReturn(
            LicensePlateError.EMPTY
        )

        viewModel.onLicensePlateChange("ABC-123")

        assertEquals(LicensePlateError.EMPTY, viewModel.uiState.value.licensePlateInputFieldValidationError)
    }

    @Test
    fun `when onLicensePlateChange is called with valid input, licensePlateError should be set to null`() {

        whenever(Log.d(any(), any())).then {
            println("test")
        }

        whenever(LicensePlate.validate(any())).thenReturn(null)

        viewModel.onLicensePlateChange("ABC-123")

        assertNull(viewModel.uiState.value.licensePlateInputFieldValidationError)
    }

//    @Test
//    fun `when onLogout is called, navController should navigate to Login`() {
//        viewModel.onLogout()
//
//        verify(navController).navigate(Route.Login.name)
//    }
}

class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
