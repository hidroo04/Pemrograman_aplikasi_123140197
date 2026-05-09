package com.example.tugas6_1231401971.ui.screen

import android.app.Application
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.tugas6_1231401971.data.repository.NoteRepository
import com.example.tugas6_1231401971.ui.viewmodel.NotesViewModel
import com.example.tugas6_1231401971.util.NetworkMonitor
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], application = Application::class)
class NotesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val repository: NoteRepository = mockk(relaxed = true)
    private val networkMonitor: NetworkMonitor = mockk(relaxed = true)

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single { repository }
                single { networkMonitor }
                factory { NotesViewModel(get()) }
            })
        }

        every { repository.getAllNotes() } returns flowOf(emptyList())
        every { networkMonitor.isConnected } returns MutableStateFlow(true)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testEmptyStateIsDisplayed() {
        composeTestRule.setContent {
            KoinContext {
                NotesScreen(onSettingsClick = {})
            }
        }

        composeTestRule.onNodeWithText("No notes found").assertIsDisplayed()
    }

    @Test
    fun testTitleIsDisplayed() {
        composeTestRule.setContent {
            KoinContext {
                NotesScreen(onSettingsClick = {})
            }
        }

        composeTestRule.onNodeWithText("My Notes").assertIsDisplayed()
    }

    @Test
    fun testAddNoteButtonIsDisplayed() {
        composeTestRule.setContent {
            KoinContext {
                NotesScreen(onSettingsClick = {})
            }
        }

        composeTestRule.onNodeWithContentDescription("Add Note").assertIsDisplayed()
    }
}
