package com.hogent.svkapp.presentation.ui.cargoTicketScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hogent.svkapp.domain.entities.CargoTicket
import com.hogent.svkapp.presentation.viewmodels.CargoTicketScreenViewModel

/**
 * Screen that displays all the cargo tickets that were unable to be sent to SVK.
 */
@Composable
fun CargoTicketScreen(
    modifier: Modifier = Modifier,
    cargoTicketScreenViewModel: CargoTicketScreenViewModel = viewModel(factory = CargoTicketScreenViewModel.Factory),
) {
    val cargoTicketScreenState by cargoTicketScreenViewModel.uiState.collectAsState()

    CargoTicketList(
        modifier = modifier,
        cargoTickets = cargoTicketScreenState.cargoTickets,
    )
}

/**
 * A list of cargo tickets.
 */
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
                        routeNumbers = it.routeNumbers.value.map { routeNumber -> routeNumber.value },
                        licensePlate = it.licensePlate.value,
                        images = it.images.value,
                    )
                }
            }
        }
    }

}
