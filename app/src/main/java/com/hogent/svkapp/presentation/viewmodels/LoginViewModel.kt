package com.hogent.svkapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.hogent.svkapp.Route

/**
 * The [ViewModel] of the login screen.
 *
 * @param navController the [NavHostController] that is used to navigate to other screens.
 */
class LoginViewModel(private val navController: NavHostController) : ViewModel() {
    /**
     * Navigates to the main screen.
     */
    fun onLogin() {
        navController.navigate(Route.Main.name)
    }
}
