package com.hogent.svkapp.presentation.ui.cargoTicketScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.auth0.android.Auth0
import com.hogent.svkapp.R
import com.hogent.svkapp.Route
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.presentation.ui.mainscreen.MainTopAppBar
import com.hogent.svkapp.presentation.viewmodels.CargoTicketScreenViewModel
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

/**
 * Screen that displays all the cargo tickets that were unable to be sent to SVK.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CargoTicketScreen(
    modifier: Modifier = Modifier,
    mainScreenViewModel: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory),
    cargoTicketScreenViewModel: CargoTicketScreenViewModel = viewModel(factory = CargoTicketScreenViewModel.Factory),
    navController: NavController,
) {
    val cargoTicketScreenState by cargoTicketScreenViewModel.uiState.collectAsState()

    val canNavigateBack = navController.previousBackStackEntry?.destination?.route != Route.Login.name

    val context = LocalContext.current
    val auth = Auth0(
        context.getString(R.string.com_auth0_client_id),
        context.getString(R.string.com_auth0_domain)
    )

    Scaffold(topBar = {
        MainTopAppBar(
            modifier = modifier,
            onLogout = {
                mainScreenViewModel.onLogout(context, auth = auth, onLogoutNavigation = {
                    navController
                        .navigate(Route.Login.name)
                })
            },
            navigateToCargoTickets = { navController.navigate(Route.CargoTickets.name) },
            user = mainScreenViewModel.user,
            canNavigateBack = canNavigateBack,
            navigateUp = { navController.navigateUp() },
        )
    }) { innerPadding ->
        CargoTicketList(
            modifier = modifier
                .padding(innerPadding),
            cargoTickets = cargoTicketScreenState.cargoTickets,
        )
    }
}

@Composable
fun CargoTicketList(
    modifier: Modifier = Modifier,
    cargoTickets: List<CargoTicket>,
) {
    if (cargoTickets.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Alle cargo tickets werden succesvol verzonden.",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Nog te verzenden cargo tickets",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp),
            )
            LazyColumn {
                items(cargoTickets) {
                    CargoTicketCard(
                        routeNumbers = it.routeNumbers.value.map { routeNumber -> routeNumber.value.toString() },
                        licensePlate = it.licensePlate.value,
                        images = it.images.value,
                    )
                }
            }
        }
    }

}
