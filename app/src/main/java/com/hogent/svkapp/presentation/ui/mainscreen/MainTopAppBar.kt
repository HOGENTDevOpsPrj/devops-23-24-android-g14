package com.hogent.svkapp.presentation.ui.mainscreen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hogent.svkapp.presentation.ui.SVKLogo

@ExperimentalMaterial3Api
@Composable
fun MainTopAppBar(onLogout: () -> Unit) {
    TopAppBar(
        modifier = Modifier.padding(start = 0.dp),
        windowInsets = WindowInsets(0),
        title = {
            SVKLogo(Modifier.height(64.dp))
        },
        actions = {
            var expanded by remember {
                mutableStateOf(false)
            }

            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

                DropdownUserInfo(username = "test user")

                DropdownMenuItem(leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.ExitToApp, contentDescription = "Uitloggen"
                    )
                }, text = {
                    Text(text = "Log Uit")
                }, onClick = onLogout)
            }
        },
    )
}

@Composable
private fun DropdownUserInfo(username: String) {
    DropdownMenuItem(leadingIcon = {
        Icon(
            imageVector = Icons.Default.AccountBox, contentDescription = "Gebruikersnaam"
        )
    }, text = { Text(text = username) }, onClick = { /*Nothing*/ })
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun MainTopAppBarPreview() {
    MainTopAppBar {}
}
