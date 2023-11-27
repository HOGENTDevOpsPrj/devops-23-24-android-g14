package com.hogent.svkapp.presentation.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hogent.svkapp.presentation.ui.SVKLogo
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.LoginViewModel

/**
 * The login screen of the app.
 *
 * @param loginViewModel the [LoginViewModel] that contains the state of the screen.
 *
 * @sample LoginScreenPreview
 * @sample LoginScreenPreviewDark
 */
@Composable
fun LoginScreen(loginViewModel: LoginViewModel, onSuccessNav: () -> Unit) {
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
                onClick = {loginViewModel.onLogin { onSuccessNav }}, modifier = Modifier.fillMaxWidth()
            )
        }
    }
    fun login() {

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
