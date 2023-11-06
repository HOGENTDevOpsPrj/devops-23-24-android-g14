package com.hogent.svkapp.presentation.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.hogent.svkapp.presentation.ui.SVKLogo
import com.hogent.svkapp.presentation.ui.TextField
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = MaterialTheme.spacing.large),
        verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.large)
    ) {
        SVKLogo(modifier = Modifier.fillMaxWidth())
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val useremail by loginViewModel.userEmail
            val password by loginViewModel.password

//            TextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = useremail,
//                label = "Email",
//                onValueChange = loginViewModel::onUserEmailChange,
//                keyboardType = KeyboardType.Email
//            )
//            TextField(
//                modifier = Modifier.fillMaxWidth(),
//                value = password,
//                label = "Password",
//                onValueChange = loginViewModel::onPasswordChange,
//                keyboardType = KeyboardType.Password,
//                visualTransformation = PasswordVisualTransformation()
//            )
            LoginButton(
                onClick = loginViewModel::onLogin, modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

