package com.example.tugas7_123140197

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.notes.db.NotesDatabase
import com.example.tugas7_123140197.db.DatabaseDriverFactory
import com.example.tugas7_123140197.db.NoteLocalDataSource
import com.example.tugas7_123140197.network.NoteApiService
import com.example.tugas7_123140197.repository.NoteRepository
import com.example.tugas7_123140197.ui.NotesScreen
import com.example.tugas7_123140197.viewmodel.NotesViewModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    val database = remember { NotesDatabase(driverFactory.createDriver()) }
    val localDataSource = remember { NoteLocalDataSource(database) }
    val httpClient = remember {
        HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }
    val apiService = remember { NoteApiService(httpClient) }
    val repository = remember { NoteRepository(apiService, localDataSource) }
    val viewModel = remember { NotesViewModel(repository) }
    MaterialTheme {
        NotesScreen(viewModel = viewModel)
    }
}