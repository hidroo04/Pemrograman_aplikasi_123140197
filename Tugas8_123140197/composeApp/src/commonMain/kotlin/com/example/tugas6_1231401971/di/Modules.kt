package com.example.tugas6_1231401971.di

import com.example.notes.db.NotesDatabase
import com.example.tugas6_1231401971.data.local.NoteLocalDataSource
import com.example.tugas6_1231401971.data.repository.NoteRepository
import com.example.tugas6_1231401971.ui.viewmodel.NotesViewModel
import com.example.tugas6_1231401971.ui.viewmodel.SettingsViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect fun platformModule(): Module

val commonModule = module {
    single { NotesDatabase(get()) }
    singleOf(::NoteLocalDataSource)
    singleOf(::NoteRepository)
    
    viewModelOf(::NotesViewModel)
    viewModelOf(::SettingsViewModel)
}
