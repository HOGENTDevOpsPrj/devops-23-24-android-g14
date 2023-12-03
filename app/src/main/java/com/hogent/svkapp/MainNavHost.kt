package com.hogent.svkapp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hogent.svkapp.presentation.ui.login.LoginScreen
import com.hogent.svkapp.presentation.ui.mainscreen.MainScreen
import com.hogent.svkapp.presentation.viewmodels.LoginViewModel
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

/**
 * The routes of the app.
 */
enum class Route {
    /**
     * The login screen.
     */
    Login,

    /**
     * The main screen.
     */
    Main,
}

/**
 * The main navigation host of the app.
 *
 * @param navController the [NavHostController] that is used to navigate to other screens.
 */
@ExperimentalMaterial3Api
@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = Route.Login.name
    ) {
        composable(route = Route.Login.name) {
            LoginScreen(loginViewModel = LoginViewModel(navController = navController))
        }
        composable(route = Route.Main.name) {
            MainScreen(
                mainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory),
                goToLogin = { navController.navigate(Route.Login.name) }
            )
        }
    }
}
