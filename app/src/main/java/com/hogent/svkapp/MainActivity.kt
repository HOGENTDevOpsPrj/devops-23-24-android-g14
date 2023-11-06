package com.hogent.svkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * The main activity of the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    /**
     * Sets the content of the activity.
     *
     * @param savedInstanceState the saved instance state.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateApplicationTheme {
                MainNavHost(
                    navController = rememberNavController()
                )
            }
        }
    }
}
