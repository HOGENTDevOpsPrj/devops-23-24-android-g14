package com.hogent.svkapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R

/**
 * The SVK logo.
 *
 * @param modifier the modifier to apply to the logo.
 *
 * @sample SVKLogo
 */
@Preview
@Composable
fun SVKLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.svk_logo_met_slogan_black),
        contentDescription = stringResource(R.string.svk_logo_content_description),
        modifier = modifier
    )
}
