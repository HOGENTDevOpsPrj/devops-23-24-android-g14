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
//    private var _userEmail = mutableStateOf(value = "")
//
//    /**
//     * The email of the user.
//     */
//    val userEmail: State<String> get() = _userEmail
//
//    private var _password = mutableStateOf(value = "")
//
//    /**
//     * The password of the user.
//     */
//    val password: State<String> get() = _password

    /**
     * Called when login button is clicked.
     */
    fun onLogin() {
        navController.navigate(Route.Main.name)
    }
}
