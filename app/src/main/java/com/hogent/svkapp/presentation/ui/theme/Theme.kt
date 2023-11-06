package com.hogent.svkapp.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Apply a consistent theme across the entire application with optional dark and dynamic color theming.
 *
 * This composable function sets up the application's theme based on the preferred color schemes (dark or light),
 * and it can optionally use dynamic color theming on supported devices (Android 12+). It wraps the content in a
 * MaterialTheme composable with the selected color scheme and typography settings.
 *
 * @param useDarkTheme Whether the dark color scheme should be used. Defaults to the system setting.
 * @param useDynamicColors Whether dynamic color theming should be used. Defaults to false. This is only supported on
 * Android 12+.
 * @param content The content of the application.
 */
@Composable
fun TemplateApplicationTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(), useDynamicColors: Boolean = false, content: @Composable () -> Unit
) {
    val colorScheme = determineColorScheme(useDarkTheme, useDynamicColors)

    ApplySideEffects(colorScheme, useDarkTheme)

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = colorScheme, typography = Typography, content = content
        )
    }
}

@Composable
private fun determineColorScheme(useDarkTheme: Boolean, useDynamicColors: Boolean) = when {
    useDynamicColors -> {
        val context = LocalContext.current
        if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    useDarkTheme -> darkColors
    else -> lightColors
}

@Composable
private fun ApplySideEffects(colorScheme: ColorScheme, useDarkTheme: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = useDarkTheme
        }
    }
}
