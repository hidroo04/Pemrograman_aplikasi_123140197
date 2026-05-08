package com.example.nutritionfoodanalysis.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = LiquidCyan,
    secondary = AccentPurpleLight,
    tertiary = GlassWhite,
    background = LiquidBlack,
    surface = LiquidDarkTeal,
    onPrimary = Color.Black,
    onBackground = LiquidText,
    onSurface = LiquidText
)

private val LightColorScheme = darkColorScheme( // Still force dark
    primary = LiquidCyan,
    secondary = AccentPurpleLight,
    tertiary = GlassWhite,
    background = LiquidBlack,
    surface = LiquidDarkTeal,
    onPrimary = Color.Black,
    onBackground = LiquidText,
    onSurface = LiquidText
)

@Composable
fun NutritionFoodAnalysisTheme(
    darkTheme: Boolean = true, // Always dark
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    // Force status bar color too if needed

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
