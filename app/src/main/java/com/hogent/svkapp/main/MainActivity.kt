package com.hogent.svkapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hogent.svkapp.features.create_ticket.CreateTicketModuleImpl
import com.hogent.svkapp.features.create_ticket.presentation.ui.CreateTicketScreen
import com.hogent.svkapp.features.upload_photo.UploadPhotoModuleImpl
import com.hogent.svkapp.main.presentation.ui.theme.TemplateApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val createTicketModule = CreateTicketModuleImpl()
        val uploadPhotoModule = UploadPhotoModuleImpl()

        setContent {
            TemplateApplicationTheme {
                CreateTicketScreen(
                    createTicketViewModel = createTicketModule.getViewModel(),
                    uploadPhotoViewModel = uploadPhotoModule.getViewModel()
                )
            }
        }
    }
}
