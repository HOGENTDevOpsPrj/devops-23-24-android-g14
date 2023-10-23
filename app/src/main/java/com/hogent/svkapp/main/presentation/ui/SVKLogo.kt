package com.hogent.svkapp.main.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R

@Preview
@Composable
fun SVKLogo(iconModifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.svk_logo_met_slogan_black),
        contentDescription = "SVK Logo",
        modifier = iconModifier
    )
}
