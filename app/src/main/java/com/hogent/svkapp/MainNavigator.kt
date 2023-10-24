package com.hogent.svkapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.hogent.svkapp.presentation.ui.SVKTopAppBar
import com.hogent.svkapp.presentation.ui.login.LoginScreen
import com.hogent.svkapp.presentation.ui.mainscreen.MainScreen
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

class MainNavigator {
    private enum class Screen {
        Login,
        CreateTicket,
    }

    @Preview
    @ExperimentalMaterial3Api
    @Composable
    fun MainNavHost() {
        val navController = rememberNavController()
        val createTicketModule = AppModuleImpl()

        // Track the current screen
        var currentScreen by remember { mutableStateOf(Screen.Login) }

        Scaffold(topBar = {
            if (currentScreen != Screen.Login) {
                SVKTopAppBar(navController, currentScreen != Screen.Login)
            }
        }) { innerPadding ->
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Login.name,
                    modifier = Modifier.padding(paddingValues = innerPadding)
                ) {
                    composable(route = Screen.Login.name) {
                        currentScreen = Screen.Login
                        TemplateApplicationTheme {
                            LoginScreen(navController = navController)
                        }
                    }
                    composable(route = Screen.CreateTicket.name) {
                        currentScreen = Screen.CreateTicket
                        TemplateApplicationTheme {
                            MainScreen(mainScreenViewModel = createTicketModule.getMainScreenViewModel())
                        }
                    }
                }
            }
        }
    }
}
