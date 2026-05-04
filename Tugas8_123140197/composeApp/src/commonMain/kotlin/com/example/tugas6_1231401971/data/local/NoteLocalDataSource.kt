package com.example.tugas6_1231401971.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.notes.db.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import com.example.notes.db.NoteEntity

class NoteLocalDataSource(database: NotesDatabase) {
    private val queries = database.notesDatabaseQueries

    fun getAllNotes(): Flow<List<NoteEntity>> {
        return queries.getAllNotes().asFlow().mapToList(Dispatchers.IO)
    }

    suspend fun insertNote(title: String, content: String) {
        queries.insertNote(
            title = title,
            content = content,
            isDone = 0L,
            updatedAt = Clock.System.now().toEpochMilliseconds()
        )
    }

    suspend fun updateNote(id: Long, title: String, content: String, isDone: Boolean) {
        queries.updateNote(
            id = id,
            title = title,
            content = content,
            isDone = if (isDone) 1L else 0L,
            updatedAt = Clock.System.now().toEpochMilliseconds()
        )
    }

    suspend fun deleteNote(id: Long) {
        queries.deleteNote(id)
    }

    suspend fun toggleDone(id: Long, currentStatus: Boolean) {
        queries.updateDoneStatus(
            isDone = if (currentStatus) 0L else 1L,
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            id = id
        )
    }

    fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return queries.searchNotes(query).asFlow().mapToList(Dispatchers.IO)
    }
}