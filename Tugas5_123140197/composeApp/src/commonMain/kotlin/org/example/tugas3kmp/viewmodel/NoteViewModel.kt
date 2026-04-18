package org.example.tugas3kmp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.tugas3kmp.data.Note
import org.example.tugas3kmp.data.dummyNotes

class NoteViewModel : ViewModel() {

    private val _notes = MutableStateFlow(dummyNotes.toMutableList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private var nextId = dummyNotes.size + 1

    fun getNote(noteId: Int): Note? {
        return _notes.value.firstOrNull { it.id == noteId }
    }

    fun toggleFavorite(noteId: Int) {
        _notes.update { currentList ->
            currentList.toMutableList().apply {
                val index = indexOfFirst { it.id == noteId }
                if (index != -1) {
                    set(index, get(index).copy(isFavorite = !get(index).isFavorite))
                }
            }
        }
    }

    fun deleteNote(noteId: Int) {
        _notes.update { it.toMutableList().apply { removeAll { n -> n.id == noteId } } }
    }

    // ← hanya 2 parameter, tanpa category
    fun addNote(title: String, content: String) {
        if (title.isBlank()) return
        val newNote = Note(id = nextId++, title = title, content = content)
        _notes.update { it.toMutableList().apply { add(newNote) } }
    }

    fun updateNote(noteId: Int, newTitle: String, newContent: String) {
        _notes.update { currentList ->
            currentList.toMutableList().apply {
                val index = indexOfFirst { it.id == noteId }
                if (index != -1) {
                    set(index, get(index).copy(title = newTitle, content = newContent))
                }
            }
        }
    }
}