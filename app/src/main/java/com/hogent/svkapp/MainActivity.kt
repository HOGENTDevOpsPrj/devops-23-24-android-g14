package com.hogent.svkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hogent.svkapp.presentation.ui.SVKApp
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * The main activity of the app.
 */
class MainActivity : ComponentActivity() {

    /**
     * Sets the content of the activity.
     *
     * @param savedInstanceState the saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TemplateApplicationTheme {
                SVKApp()
            }
        }
    }
}
