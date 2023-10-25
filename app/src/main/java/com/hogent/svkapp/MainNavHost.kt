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

enum class Route {
    Login, Main,
}

@ExperimentalMaterial3Api
@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = Route.Login.name
    ) {
        composable(route = Route.Login.name) {
            LoginScreen(loginViewModel = LoginViewModel(navController = navController))
        }
        composable(route = Route.Main.name) {
            MainScreen(mainScreenViewModel = MainScreenViewModel(navController = navController))
        }
    }
}
