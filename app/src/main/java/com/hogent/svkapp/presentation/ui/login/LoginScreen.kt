package com.hogent.svkapp.presentation.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hogent.svkapp.presentation.ui.SVKLogo
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = MaterialTheme.spacing.large),
        verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.large)
    ) {
        SVKLogo(modifier = Modifier.fillMaxWidth())
        LoginButton(
            onClick = loginViewModel::onLogin, modifier = Modifier.fillMaxWidth()
        )
    }
}
