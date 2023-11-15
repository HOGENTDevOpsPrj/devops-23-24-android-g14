package com.hogent.svkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme
import com.hogent.svkapp.presentation.viewmodels.LoginViewModel

/**
 * The main activity of the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    // this breaks: smth with navhostcontroller?
//    private val loginViewModel:LoginViewModel by viewModels()

    /**
     * Sets the content of the activity.
     *
     * @param savedInstanceState the saved instance state.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // necessary for login
//        loginViewModel.setContext(this)

        setContent {
            TemplateApplicationTheme {
                MainNavHost(
                    navController = rememberNavController()
                )
            }
        }
    }
}
