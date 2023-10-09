package com.example.templateapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.templateapplication.ui.theme.TemplateApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                ) {

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTicket() {
    var text by rememberSaveable { mutableStateOf("") }
    Image(
        painter = painterResource(id = R.drawable.svk_logo_zonder_slogan),
        contentDescription = "SVK Logo",
        modifier = Modifier.height(64.dp)
    )
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Lading registreren", maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }
        }, actions = {
            IconButton(onClick = {/*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp, contentDescription = "Uitloggen"
                )
            }
        })
    }, floatingActionButton = {
        LargeFloatingActionButton(
            onClick = { },
        ) {
            Icon(Icons.Default.Send, "Large floating action button")
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable._15_svk_logo_met_slogan_black_01),
                contentDescription = "SVK Logo"
            )
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Routenummer") },
                placeholder = { Text("Routenummer") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Nummerplaat") },
                placeholder = { Text("Nummerplaat") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text("Voeg foto toe")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateTicketPreview() {
    TemplateApplicationTheme(useDarkTheme = false) {
        CreateTicket()
    }
}