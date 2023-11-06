package com.hogent.svkapp.presentation.ui.mainscreen

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * A floating action button that is used to send a cargo ticket.
 *
 * @param onSend a callback that is called when the user clicks on the button.
 * @param modifier the modifier to apply to the button.
 *
 * @sample SendFloatingActionButtonPreview
 * @sample SendFloatingActionButtonPreviewDark
 */
@Composable
fun SendFloatingActionButton(onSend: () -> Unit, modifier: Modifier = Modifier) {
    ExtendedFloatingActionButton(onClick = onSend, icon = {
        Icon(
            imageVector = Icons.Default.Send,
            contentDescription = stringResource(R.string.send_button_text)
        )
    }, text = { Text(text = stringResource(R.string.send_button_text)) }, modifier = modifier)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SendFloatingActionButtonPreview() {
    TemplateApplicationTheme {
        SendFloatingActionButton(onSend = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SendFloatingActionButtonPreviewDark() = SendFloatingActionButtonPreview()
