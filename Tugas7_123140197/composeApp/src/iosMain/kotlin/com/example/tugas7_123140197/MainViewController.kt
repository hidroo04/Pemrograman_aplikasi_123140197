package com.example.tugas7_123140197

import androidx.compose.ui.window.ComposeUIViewController
import com.example.tugas7_123140197.db.DatabaseDriverFactory

fun MainViewController() = ComposeUIViewController {
    App(driverFactory = DatabaseDriverFactory())
}