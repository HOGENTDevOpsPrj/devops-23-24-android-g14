package com.hogent.svkapp.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hogent.svkapp.features.create_ticket.CreateTicketModuleImpl
import com.hogent.svkapp.features.create_ticket.presentation.ui.CreateTicketScreen
import com.hogent.svkapp.features.login.presentation.ui.LoginScreen
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

class MainNavigator {

    private enum class Screen {
        LOGIN, CREATE_TICKET
    }

    @Preview
    @ExperimentalMaterial3Api
    @Composable
    fun MainNavHost() {
        val navController = rememberNavController()
        val createTicketModule = CreateTicketModuleImpl()

        // Track the current screen
        var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

        Scaffold(topBar = {
            if (currentScreen != Screen.LOGIN) {
                TopAppBar(title = { Text("SVK") }, actions = {
                    IconButton(onClick = {
                        navController.navigate("login") {
                            popUpTo("login") {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null)
                    }
                })
            }
        }) { innerPadding ->
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "login",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("login") {
                        currentScreen = Screen.LOGIN
                        TemplateApplicationTheme {
                            LoginScreen(navController = navController)
                        }
                    }
                    composable("createTicket") {
                        currentScreen = Screen.CREATE_TICKET
                        TemplateApplicationTheme {
                            CreateTicketScreen(createTicketScreenViewModel = createTicketModule.getViewModel())
                        }
                    }
                }
            }
        }
    }
}
