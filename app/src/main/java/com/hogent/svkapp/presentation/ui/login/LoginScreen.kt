package com.hogent.svkapp.presentation.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.auth0.android.Auth0
import com.hogent.svkapp.R
import com.hogent.svkapp.presentation.ui.navigation.Route
import com.hogent.svkapp.presentation.ui.SVKLogo
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

/**
 * The login screen of the app.
 *
 * @param loginViewModel the [LoginViewModel] that contains the state of the screen.
 *
 * @sample LoginScreenPreview
 * @sample LoginScreenPreviewDark
 */
@Composable
fun LoginScreen(
    viewModel: MainScreenViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = MaterialTheme.spacing.large),
        verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.large)
    ) {
        SVKLogo(modifier = Modifier.fillMaxWidth())
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoginButton(
                onClick = {
                    viewModel.onLogin(context, onSuccessNavigation = {
                        navController.navigate(Route.Main.name)
                    })
                },
                modifier =
                Modifier.fillMaxWidth()
            )
        }
    }
}


//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun LoginScreenPreview() {
//    LoginScreen(loginViewModel = LoginViewModel(navController = rememberNavController()))
//}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun LoginScreenPreviewDark() = LoginScreenPreview()
