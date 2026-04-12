package org.example.tugas3kmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.tugas3kmp.ui.MainScreen

/**
 * App.kt — UPDATED
 * Ganti AppNavigation() → MainScreen()
 * MainScreen sudah include Bottom Nav + semua route
 */
@Composable
fun App() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen()
        }
    }
}