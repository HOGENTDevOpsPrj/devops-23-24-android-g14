package com.hogent.svkapp.presentation.ui.qrScanner

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hogent.svkapp.R

@Composable
fun QrScanButton(
    modifier: Modifier = Modifier,
    navigateToQrScanner: () -> Unit,
) {
    IconButton(
        modifier = modifier
            .padding(0.dp),
        onClick = navigateToQrScanner,
    ) {
        Icon(
            tint = MaterialTheme.colorScheme.onSurface,
            painter = painterResource(id = R.drawable.qr_icon),
            contentDescription = stringResource(
                R.string.scan_route_number_button_content_description
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QrScanButtonPreview() {
    QrScanButton(
        navigateToQrScanner = {},
    )
}