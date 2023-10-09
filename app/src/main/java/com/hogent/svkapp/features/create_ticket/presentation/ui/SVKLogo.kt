package com.hogent.svkapp.features.create_ticket.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R

@Preview
@Composable
fun SVKLogo() {
    Image(
        painter = painterResource(id = R.drawable._15_svk_logo_met_slogan_black_01),
        contentDescription = "SVK Logo"
    )
}
