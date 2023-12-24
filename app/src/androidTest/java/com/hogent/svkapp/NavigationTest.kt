package com.hogent.svkapp

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.hogent.svkapp.presentation.ui.SVKApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composableTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        composableTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SVKApp(navController = navController)
        }
    }

    @Test
    fun verifyStartDestination() {
        composableTestRule
            .onNodeWithText("Login")
            .assertIsDisplayed()
    }

//    @Test
//    fun navigateToForm() {
//        composableTestRule
//            .onNodeWithText("Login")
//            .performClick()
//        composableTestRule
//            .onNodeWithText("Routenummer")
//            .assertIsDisplayed()
//    }
}