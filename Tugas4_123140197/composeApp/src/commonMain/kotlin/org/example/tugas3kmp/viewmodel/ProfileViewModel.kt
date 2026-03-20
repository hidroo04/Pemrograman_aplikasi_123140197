package org.example.tugas3kmp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.tugas3kmp.viewmodel.ProfileUiState

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())

    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    fun toggleDarkMode() {
        _uiState.update { currentState ->
            currentState.copy(isDarkMode = !currentState.isDarkMode)
        }
    }

    fun openEditMode() {
        _uiState.update { currentState ->
            currentState.copy(
                isEditMode = true,
                editName = currentState.name,  // pre-fill dengan data sekarang
                editBio = currentState.bio
            )
        }
    }

    fun closeEditMode() {
        _uiState.update { it.copy(isEditMode = false) }
    }
    fun onEditNameChange(newName: String) {
        _uiState.update { it.copy(editName = newName) }
    }
    fun onEditBioChange(newBio: String) {
        _uiState.update { it.copy(editBio = newBio) }
    }

    fun saveProfile() {
        _uiState.update { currentState ->
            currentState.copy(
                name = currentState.editName.ifBlank { currentState.name },
                bio = currentState.editBio.ifBlank { currentState.bio },
                initials = generateInitials(currentState.editName),
                isEditMode = false
            )
        }
    }

    // ── Helper: generate inisial dari nama ────────────────────────
    // "Budi Santoso" → "BS"
    private fun generateInitials(name: String): String {
        return name.trim()
            .split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .map { it.first().uppercaseChar() }
            .joinToString("")
            .ifBlank { "??" }
    }
}