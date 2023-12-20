package com.hogent.svkapp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hogent.svkapp.presentation.ui.cargoTicketScreen.CargoTicketScreen
import com.hogent.svkapp.presentation.ui.login.LoginScreen
import com.hogent.svkapp.presentation.ui.mainscreen.MainScreen
import com.hogent.svkapp.presentation.ui.qrScanner.QrPreviewView
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

    /**
     * The cargo tickets screen.
     */
    CargoTickets,

    /**
     * The qr scanner screen.
     */
    QrScanner,
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
            LoginScreen(
                viewModel,
                navController
            )
        }
        composable(route = Route.Main.name) {
            MainScreen(
                mainScreenViewModel = viewModel,
                navController = navController,
                mainScreenViewModel = viewModel
            )
        }
        composable(route = Route.CargoTickets.name) {
            CargoTicketScreen(
                mainScreenViewModel = viewModel,
                navController = navController,
            )
        }
        composable("${Route.QrScanner.name}/{index}") {
            QrPreviewView(
                mainScreenViewModel = viewModel,
                navController = navController,
                index = it.arguments?.getString("index")?.toInt() ?: 0,
            )
        }
    }
}
