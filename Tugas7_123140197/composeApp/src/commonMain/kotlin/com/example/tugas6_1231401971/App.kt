package com.example.tugas6_1231401971

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import app.cash.sqldelight.db.SqlDriver
import com.example.tugas6_1231401971.data.local.NoteLocalDataSource
import com.example.tugas6_1231401971.data.repository.NoteRepository
import com.example.notes.db.DatabaseDriverFactory
import com.example.notes.db.NotesDatabase
import com.example.tugas6_1231401971.ui.screen.NotesScreen
import com.example.tugas6_1231401971.ui.viewmodel.NotesViewModel

@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    val database = remember { NotesDatabase(driverFactory.createDriver()) }
    val dataSource = remember { NoteLocalDataSource(database) }
    val repository = remember { NoteRepository(dataSource) }
    val viewModel = viewModel { NotesViewModel(repository) }

    NotesScreen(viewModel = viewModel)
}