package com.example.tugas6_1231401971.data.repository

import com.example.tugas6_1231401971.data.local.NoteLocalDataSource
import com.example.notes.db.NoteEntity
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val localDataSource: NoteLocalDataSource) {
    fun getAllNotes(): Flow<List<NoteEntity>> = localDataSource.getAllNotes()

    suspend fun addNote(title: String, content: String) {
        localDataSource.insertNote(title, content)
    }

    suspend fun updateNote(id: Long, title: String, content: String, isDone: Boolean) {
        localDataSource.updateNote(id, title, content, isDone)
    }

    suspend fun deleteNote(id: Long) {
        localDataSource.deleteNote(id)
    }

    suspend fun toggleDone(id: Long, currentStatus: Boolean) {
        localDataSource.toggleDone(id, currentStatus)
    }

    fun searchNotes(query: String): Flow<List<NoteEntity>> = localDataSource.searchNotes(query)
}