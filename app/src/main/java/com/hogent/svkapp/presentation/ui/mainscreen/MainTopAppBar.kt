package com.hogent.svkapp.presentation.ui.mainscreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hogent.svkapp.R
import com.hogent.svkapp.domain.entities.User
import com.hogent.svkapp.presentation.ui.SVKLogo
import com.hogent.svkapp.presentation.ui.theme.TemplateApplicationTheme

/**
 * The top app bar of the main screen.
 *
 * @param onLogout a callback that is called when the user clicks on the logout button.
 *
 * @sample MainTopAppBarPreview
 */
@ExperimentalMaterial3Api
@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onLogout: () -> Unit,
    navigateToCargoTickets: () -> Unit,
    navigateToQrScanner: () -> Unit,
    user: User,
) {
    TopAppBar(
        modifier = modifier.padding(start = 0.dp),
        windowInsets = WindowInsets(0),
        title = {
            SVKLogo(Modifier.height(64.dp))
        },
        actions = {
            var expanded by remember {
                mutableStateOf(false)
            }

            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more_options_button_content_description)
                )
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

                DropdownUserInfo(username = user.name)

                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = stringResource(R.string.user_icon_content_description)
                        )
                    },
                    text = { Text(text = "Cargo Tickets") },
                    onClick = navigateToCargoTickets,
                )

                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = stringResource(R.string.user_icon_content_description)
                        )
                    },
                    text = { Text(text = "Scan QR code") },
                    onClick = navigateToQrScanner,
                )

                DropdownMenuItem(leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = stringResource(R.string.logout_button_content_description)
                    )
                }, text = {
                    Text(text = stringResource(R.string.logout_button_text))
                }, onClick = onLogout)
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "navigate back",
                    )
                }
            }
        },
    )
}

@Composable
private fun DropdownUserInfo(username: String) {
    DropdownMenuItem(leadingIcon = {
        Icon(
            imageVector = Icons.Default.AccountBox,
            contentDescription = stringResource(R.string.user_icon_content_description)
        )
    }, text = { Text(text = username) }, onClick = { /*Nothing*/ })
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun MainTopAppBarPreview() {
    TemplateApplicationTheme {
        MainTopAppBar(
            onLogout = {},
            user = User(),
            navigateToCargoTickets = {},
            canNavigateBack = false,
            navigateUp = {},
            navigateToQrScanner = {},
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainTopAppBarPreviewDark() = MainTopAppBarPreview()
