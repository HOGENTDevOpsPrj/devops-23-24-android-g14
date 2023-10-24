package com.hogent.svkapp

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hogent.svkapp.presentation.ui.CreateTicketScreen
import com.hogent.svkapp.presentation.ui.login.LoginScreen
import com.hogent.svkapp.presentation.ui.SVKLogo
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

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
                SVKTopAppBar(navController)
            }
        }) { innerPadding ->
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "login",
                    modifier = Modifier.padding(paddingValues = innerPadding)
                ) {
                    composable(route = "login") {
                        currentScreen = Screen.LOGIN
                        TemplateApplicationTheme {
                            LoginScreen(navController = navController)
                        }
                    }
                    composable(route = "createTicket") {
                        currentScreen = Screen.CREATE_TICKET
                        TemplateApplicationTheme {
                            CreateTicketScreen(createTicketScreenViewModel = createTicketModule.getViewModel())
                        }
                    }
                }
            }
        }
    }

    @ExperimentalMaterial3Api
    @Composable
    private fun SVKTopAppBar(navController: NavHostController) {
        TopAppBar(
//            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
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

                    DropdownMenuItem(leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Gebruikersnaam"
                        )
                    }, text = { Text(text = "Gebruiker") }, onClick = { /*Nothing*/ })

                    DropdownMenuItem(leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ExitToApp, contentDescription = "Uitloggen"
                        )
                    }, text = {
                        Text(text = "Log Uit")
                    }, onClick = {
                        navController.navigate(route = "login") {
                            popUpTo(route = "login") {
                                inclusive = true
                            }
                        }
                    })
                }
            },
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    private fun SVKTopAppBarPreview() {
        SVKTopAppBar(navController = rememberNavController())
    }

}
