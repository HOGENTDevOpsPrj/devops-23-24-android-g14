package com.hogent.svkapp.presentation.ui.mainscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hogent.svkapp.presentation.ui.navigation.Route
import com.hogent.svkapp.presentation.ui.theme.spacing
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

/**
 * The main screen of the app.
 *
 * @param viewModel the [MainScreenViewModel] that contains the state of the screen.
 *
 * @sample MainScreenPreview
 * @sample MainScreenPreviewDark
 */
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory),
    navController: NavController,
) {
    val mainScreenState by viewModel.uiState.collectAsState()

    val navigateToQrScanner: (Int) -> Unit = { index ->
        navController.navigate("${Route.QrScanner.name}/$index")
    }
    Form(
        modifier = modifier
            .fillMaxSize()
            .padding(all = MaterialTheme.spacing.large),
        mainScreenViewModel = viewModel,
        navigateToQrScanner = navigateToQrScanner,
    )
    if (mainScreenState.showPopup) {
        ConfirmationDialog(onDismissRequest = viewModel::toggleDialog)
    }
}


//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun MainScreenPreview() {
//    TemplateApplicationTheme {
//        MainScreen(
//            mainScreenViewModel = MainScreenViewModel(navController = rememberNavController())
//        )
//    }
//}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun MainScreenPreviewDark() = MainScreenPreview()
