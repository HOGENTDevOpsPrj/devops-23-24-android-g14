package com.hogent.svkapp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.hogent.svkapp.Route

class LoginViewModel(private val navController: NavHostController) : ViewModel() {

    private var _userEmail = mutableStateOf(value="")
    val userEmail: State<String> get() = _userEmail

    private var _password = mutableStateOf(value="")
    val password: State<String> get() = _password

    fun onUserEmailChange(userEmail: String) {
        _userEmail.value = userEmail
        // validatie via LoginValidator
    }

    fun onPasswordChange(password: String) {
        _password.value = password
        // validatie / encryptie ??
    }

    fun onLogin() {
        navController.navigate(Route.Main.name)
    }
}
