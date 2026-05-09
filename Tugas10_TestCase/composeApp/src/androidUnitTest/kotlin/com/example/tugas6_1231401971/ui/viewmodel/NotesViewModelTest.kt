package com.example.tugas6_1231401971.ui.viewmodel

import app.cash.turbine.test
import com.example.notes.db.NoteEntity
import com.example.tugas6_1231401971.data.repository.NoteRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    private lateinit var viewModel: NotesViewModel
    private val repository: NoteRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { repository.getAllNotes() } returns flowOf(emptyList())
        every { repository.searchNotes(any()) } returns flowOf(emptyList())
        viewModel = NotesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun searchQueryChangeShouldUpdateSearchQueryState() = runTest {
        val query = "test query"
        viewModel.onSearchQueryChange(query)
        assertEquals(query, viewModel.searchQuery.value)
    }

    @Test
    fun addNoteShouldCallRepositoryAddNote() = runTest {
        coEvery { repository.addNote(any(), any()) } just Runs
        viewModel.addNote("Title", "Content")
        advanceUntilIdle()
        coVerify { repository.addNote("Title", "Content") }
    }

    @Test
    fun deleteNoteShouldCallRepositoryDeleteNote() = runTest {
        coEvery { repository.deleteNote(any()) } just Runs
        viewModel.deleteNote(1L)
        advanceUntilIdle()
        coVerify { repository.deleteNote(1L) }
    }

    @Test
    fun toggleDoneShouldCallRepositoryToggleDone() = runTest {
        val note = NoteEntity(1, "Title", "Content", 0, 1000L)
        coEvery { repository.toggleDone(any(), any()) } just Runs
        viewModel.toggleDone(note)
        advanceUntilIdle()
        coVerify { repository.toggleDone(1L, false) }
    }

    @Test
    fun notesFlowShouldEmitInitialEmptyList() = runTest {
        viewModel.notes.test {
            assertEquals(emptyList(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun notesFlowShouldEmitItemsFromRepository() = runTest {
        val expectedNotes = listOf(NoteEntity(1, "Title", "Content", 0, 1000L))
        every { repository.getAllNotes() } returns flowOf(expectedNotes)
        
        val vm = NotesViewModel(repository)
        vm.notes.test {
            // StateFlow emits the initial value first
            assertEquals(emptyList(), awaitItem())
            assertEquals(expectedNotes, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
