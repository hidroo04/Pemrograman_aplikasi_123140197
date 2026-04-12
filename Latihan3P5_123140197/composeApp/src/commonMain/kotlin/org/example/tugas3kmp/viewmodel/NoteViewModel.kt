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

    private val _editingNote = MutableStateFlow<Note?>(null)
    val editingNote: StateFlow<Note?> = _editingNote.asStateFlow()

    private var nextId = dummyNotes.size + 1

    fun deleteNote(noteId: Int) {
        _notes.update { currentList ->
            currentList.toMutableList().apply {
                removeAll { it.id == noteId }
            }
        }
    }


    fun startEdit(note: Note) {
        _editingNote.value = note
    }


    fun cancelEdit() {
        _editingNote.value = null
    }


    fun updateNote(noteId: Int, newTitle: String, newContent: String) {
        _notes.update { currentList ->
            currentList.toMutableList().apply {
                val index = indexOfFirst { it.id == noteId }
                if (index != -1) {
                    set(index, get(index).copy(
                        title = newTitle,
                        content = newContent
                    ))
                }
            }
        }
        _editingNote.value = null
    }


    fun addNote(title: String, content: String) {
        if (title.isBlank()) return
        val newNote = Note(
            id = nextId++,
            title = title,
            content = content
        )
        _notes.update { currentList ->
            currentList.toMutableList().apply { add(newNote) }
        }
    }
}