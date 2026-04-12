package org.example.tugas3kmp

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "My Profile App",                    // Judul window
        state = rememberWindowState(
            width = 400.dp,                          // Lebar window
            height = 700.dp                          // Tinggi window
        )
    ) {
        App()  // Memanggil App() yang sama dengan Android & iOS
    }
}
 