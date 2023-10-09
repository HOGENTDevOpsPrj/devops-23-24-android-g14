package com.hogent.svkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hogent.svkapp.features.create_ticket.presentation.ui.CreateTicketScreen
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateApplicationTheme {
                // A surface container using the 'background' color from the theme
                CreateTicketScreen()
            }
        }
    }
}
