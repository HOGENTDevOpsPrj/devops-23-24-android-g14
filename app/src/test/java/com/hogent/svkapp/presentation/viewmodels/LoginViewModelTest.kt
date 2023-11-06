//package com.hogent.svkapp.presentation.viewmodels
//
//import androidx.navigation.NavHostController
//import com.hogent.svkapp.Route
//import org.junit.Before
//import org.junit.Test
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.verify
//
//class LoginViewModelTest {
//    private lateinit var navController: NavHostController
//    private lateinit var viewModel: LoginViewModel
//
//    @Before
//    fun setUp() {
//        navController = mock()
//        viewModel = LoginViewModel(navController)
//    }
//
//    @Test
//    fun `onLogin navigates to the main route`() {
//        viewModel.onLogin()
//
//        verify(navController).navigate(Route.Main.name)
//    }
//}
