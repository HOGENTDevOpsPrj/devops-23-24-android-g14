package com.hogent.svkapp.presentation.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.auth0.android.Auth0
import com.hogent.svkapp.R
import com.hogent.svkapp.presentation.ui.cargoTicketScreen.CargoTicketScreen
import com.hogent.svkapp.presentation.ui.login.LoginScreen
import com.hogent.svkapp.presentation.ui.mainscreen.MainScreen
import com.hogent.svkapp.presentation.ui.mainscreen.MainTopAppBar
import com.hogent.svkapp.presentation.ui.mainscreen.SendFloatingActionButton
import com.hogent.svkapp.presentation.ui.qrScanner.QrPreviewView
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

/**
 * The main navigation host of the app.
 *
 * @param navController the [NavHostController] that is used to navigate to other screens.
 */
@ExperimentalMaterial3Api
@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: MainScreenViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.name,
    ) {

        val canNavigateBack =
            navController.currentBackStackEntry?.destination?.route != Route.Main.name || navController
                .previousBackStackEntry?.destination?.route != Route.Login.name

        composable(route = Route.Login.name) {
            LoginScreen(
                viewModel,
                navController
            )
        }
        composable(route = Route.Main.name) {

            val mainScreenState by viewModel.uiState.collectAsState()

            val context = LocalContext.current

            val auth = Auth0(
                context.getString(R.string.com_auth0_client_id),
                context.getString(R.string.com_auth0_domain)
            )

            Scaffold(floatingActionButton = {
                SendFloatingActionButton(onSend = viewModel::onSend)
            }, topBar = {
                MainTopAppBar(
                    onLogout = {
                        viewModel.onLogout(context, auth = auth, onLogoutNavigation = {
                            navController.navigate(Route.Login.name)
                        })
                    },
                    navigateToCargoTickets = { navController.navigate(Route.CargoTickets.name) },
                    canNavigateBack = false,
                    navigateUp = { navController.navigateUp() },
                    user = mainScreenState.user,
                )
            }) { innerPadding ->
                MainScreen(
                    viewModel = viewModel,
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
        composable(route = Route.CargoTickets.name) {

            val mainScreenState by viewModel.uiState.collectAsState()

            val context = LocalContext.current

            val auth = Auth0(
                context.getString(R.string.com_auth0_client_id),
                context.getString(R.string.com_auth0_domain)
            )

            Scaffold(floatingActionButton = {
                SendFloatingActionButton(onSend = viewModel::onSend)
            }, topBar = {
                MainTopAppBar(
                    onLogout = {
                        viewModel.onLogout(context, auth = auth, onLogoutNavigation = {
                            navController.navigate(Route.Login.name)
                        })
                    },
                    navigateToCargoTickets = { navController.navigate(Route.CargoTickets.name) },
                    canNavigateBack = canNavigateBack,
                    navigateUp = { navController.navigateUp() },
                    user = mainScreenState.user,
                )
            }) { innerPadding ->
                CargoTicketScreen(
                    modifier = Modifier.padding(innerPadding),
                )
            }
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
