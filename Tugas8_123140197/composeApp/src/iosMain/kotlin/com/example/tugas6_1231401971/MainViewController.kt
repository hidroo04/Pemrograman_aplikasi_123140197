package com.example.tugas6_1231401971

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.example.notes.db.DatabaseDriverFactory

fun MainViewController() = ComposeUIViewController {
    val driverFactory = remember { DatabaseDriverFactory() }
    MaterialTheme {
        App(driverFactory)
    }
}