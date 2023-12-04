package com.hogent.svkapp.presentation.ui.mainscreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.auth0.android.Auth0
import com.hogent.svkapp.R
import com.hogent.svkapp.Route
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

/**
 * The main screen of the app.
 *
 * @param mainScreenViewModel the [MainScreenViewModel] that contains the state of the screen.
 *
 * @sample MainScreenPreview
 * @sample MainScreenPreviewDark
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory),
    goToLogin: () -> Unit,
) {
    val mainScreenState by mainScreenViewModel.uiState.collectAsState()

    val context = LocalContext.current
    val auth = Auth0(
        context.getString(R.string.com_auth0_client_id),
        context.getString(R.string.com_auth0_domain)
    )
    val user = mainScreenViewModel.user

    Scaffold(floatingActionButton = {
        SendFloatingActionButton(onSend = mainScreenViewModel::onSend)
    }, topBar = {
        MainTopAppBar(onLogout = {
            mainScreenViewModel.onLogout(context, auth = auth, onLogoutNavigation = {
                goToLogin()
            })
        }, user)
    }) { innerPadding ->
        Form(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(all = MaterialTheme.spacing.large),
            mainScreenViewModel = mainScreenViewModel,
        )
        if (mainScreenState.showPopup) {
            ConfirmationDialog(onDismissRequest = mainScreenViewModel::toggleDialog)
        }
    }
}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun MainScreenPreview() {
//    TemplateApplicationTheme {
//        MainScreen(
//            mainScreenViewModel = MainScreenViewModel(navController = rememberNavController())
//        )
//    }
//}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun MainScreenPreviewDark() = MainScreenPreview()
