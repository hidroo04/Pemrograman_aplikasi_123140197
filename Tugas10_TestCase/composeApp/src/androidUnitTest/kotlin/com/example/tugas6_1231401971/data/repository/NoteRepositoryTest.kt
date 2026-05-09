package com.example.tugas6_1231401971.data.repository

import com.example.notes.db.NoteEntity
import com.example.tugas6_1231401971.data.local.NoteLocalDataSource
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class NoteRepositoryTest {
    private lateinit var repository: NoteRepository
    private val localDataSource: NoteLocalDataSource = mockk()

    @Before
    fun setup() {
        repository = NoteRepository(localDataSource)
    }

    @Test
    fun getAllNotesShouldCallLocalDataSource() = runTest {
        val expectedNotes = listOf(
            NoteEntity(1, "Title 1", "Content 1", 0, 1000L),
            NoteEntity(2, "Title 2", "Content 2", 1, 2000L)
        )
        every { localDataSource.getAllNotes() } returns flowOf(expectedNotes)

        repository.getAllNotes().collect { notes ->
            assertEquals(expectedNotes, notes)
        }
        verify { localDataSource.getAllNotes() }
    }

    @Test
    fun addNoteShouldCallLocalDataSourceInsertNote() = runTest {
        coEvery { localDataSource.insertNote(any(), any()) } just Runs
        repository.addNote("New Title", "New Content")
        coVerify { localDataSource.insertNote("New Title", "New Content") }
    }

    @Test
    fun updateNoteShouldCallLocalDataSourceUpdateNote() = runTest {
        coEvery { localDataSource.updateNote(any(), any(), any(), any()) } just Runs
        repository.updateNote(1L, "Updated Title", "Updated Content", true)
        coVerify { localDataSource.updateNote(1L, "Updated Title", "Updated Content", true) }
    }

    @Test
    fun deleteNoteShouldCallLocalDataSourceDeleteNote() = runTest {
        coEvery { localDataSource.deleteNote(any()) } just Runs
        repository.deleteNote(1L)
        coVerify { localDataSource.deleteNote(1L) }
    }

    @Test
    fun toggleDoneShouldCallLocalDataSourceToggleDone() = runTest {
        coEvery { localDataSource.toggleDone(any(), any()) } just Runs
        repository.toggleDone(1L, false)
        coVerify { localDataSource.toggleDone(1L, false) }
    }

    @Test
    fun searchNotesShouldCallLocalDataSourceSearchNotes() = runTest {
        val query = "test"
        val expectedNotes = listOf(NoteEntity(1, "test title", "content", 0, 1000L))
        every { localDataSource.searchNotes(query) } returns flowOf(expectedNotes)

        repository.searchNotes(query).collect { notes ->
            assertEquals(expectedNotes, notes)
        }
        verify { localDataSource.searchNotes(query) }
    }
}
