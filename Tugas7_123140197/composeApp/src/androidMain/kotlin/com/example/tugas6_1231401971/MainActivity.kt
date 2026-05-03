package com.example.tugas6_1231401971

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.remember
import com.example.notes.db.DatabaseDriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val driverFactory = remember { DatabaseDriverFactory(applicationContext) }
            MaterialTheme {
                App(driverFactory)   // <-- Passing the driver factory
            }
        }
    }
}