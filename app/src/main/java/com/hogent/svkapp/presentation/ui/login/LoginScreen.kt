package com.hogent.svkapp.presentation.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hogent.svkapp.presentation.ui.SVKLogo
import com.hogent.svkapp.presentation.ui.theme.spacing

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(all = MaterialTheme.spacing.large),
        verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.large)
    ) {
        SVKLogo(modifier = Modifier.fillMaxWidth())
        LoginButton(
            onClick = { navController.navigate("createTicket") }, modifier = Modifier.fillMaxWidth()
        )
    }
}
