package com.example.tugas6_1231401971

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.tugas6_1231401971.ui.screen.NotesScreen
import com.example.tugas6_1231401971.ui.screen.SettingsScreen
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        var isSettingsOpen by remember { mutableStateOf(false) }

        if (isSettingsOpen) {
            SettingsScreen(onBack = { isSettingsOpen = false })
        } else {
            NotesScreen(onSettingsClick = { isSettingsOpen = true })
        }
    }
}