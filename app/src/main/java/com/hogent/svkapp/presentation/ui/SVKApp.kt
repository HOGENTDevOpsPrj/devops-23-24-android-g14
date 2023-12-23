package com.hogent.svkapp.presentation.ui

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hogent.svkapp.presentation.ui.navigation.MainNavHost
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SVKApp(
    navController: NavHostController = rememberNavController(),
) {
    Log.d("SVKApp", "SVKApp")

    MainNavHost(
        navController = navController,
        viewModel = viewModel(factory = MainScreenViewModel.Factory),
    )
}