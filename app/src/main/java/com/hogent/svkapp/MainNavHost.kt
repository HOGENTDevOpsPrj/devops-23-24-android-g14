package com.hogent.svkapp

import android.app.Application
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
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
    navController: NavHostController,
    viewModel: MainScreenViewModel
) {
    NavHost(
        navController = navController, startDestination = Route.Login.name
    ) {
        composable(route = Route.Login.name) {
            LoginScreen(viewModel, navController)
        }
        composable(route = Route.Main.name) {
            MainScreen(mainScreenViewModel = viewModel, navController)
        }
    }
}
