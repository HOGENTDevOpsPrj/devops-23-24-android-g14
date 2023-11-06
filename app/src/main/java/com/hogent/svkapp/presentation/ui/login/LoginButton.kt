package com.hogent.svkapp.presentation.ui.login

import android.content.res.Configuration
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hogent.svkapp.R
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * A button that is used to login.
 *
 * @param onClick the action that is performed when the button is clicked.
 * @param modifier the modifier for this composable.
 *
 * @sample LoginButtonPreview
 * @sample LoginButtonPreviewDark
 */
@Composable
fun LoginButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick, modifier = modifier
    ) {
        Text(text = stringResource(R.string.login_button_text))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LoginButtonPreview() {
    TemplateApplicationTheme {
        LoginButton(onClick = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LoginButtonPreviewDark() = LoginButtonPreview()
