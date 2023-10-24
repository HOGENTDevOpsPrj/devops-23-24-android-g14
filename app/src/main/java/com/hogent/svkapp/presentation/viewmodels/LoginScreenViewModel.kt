package com.hogent.svkapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hogent.svkapp.Screen

class LoginViewModel(private val navController: NavController) : ViewModel() {
    fun onLogin() {
        navController.navigate(Screen.Main.name)
    }
}
