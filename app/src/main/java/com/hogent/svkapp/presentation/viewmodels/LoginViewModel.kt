package com.hogent.svkapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.hogent.svkapp.Route

class LoginViewModel(private val navController: NavHostController) : ViewModel() {
    fun onLogin() {
        navController.navigate(Route.Main.name)
    }
}
