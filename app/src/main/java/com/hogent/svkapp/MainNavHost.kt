package com.hogent.svkapp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hogent.svkapp.presentation.ui.login.LoginScreen
import com.hogent.svkapp.presentation.ui.mainscreen.MainScreen
import com.hogent.svkapp.presentation.viewmodels.LoginViewModel
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

enum class Screen {
    Login, Main,
}

@ExperimentalMaterial3Api
@Composable
fun MainNavHost(
    mainScreenViewModel: MainScreenViewModel,
    loginViewModel: LoginViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = Screen.Login.name
    ) {
        composable(route = Screen.Login.name) {
            LoginScreen(loginViewModel = loginViewModel)
        }
        composable(route = Screen.Main.name) {
            MainScreen(mainScreenViewModel = mainScreenViewModel)
        }
    }
}
