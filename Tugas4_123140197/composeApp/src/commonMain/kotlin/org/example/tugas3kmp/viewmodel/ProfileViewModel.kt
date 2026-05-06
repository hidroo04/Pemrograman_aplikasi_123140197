package org.example.tugas3kmp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
                editName = currentState.name,
                editBio = currentState.bio,
                editEmail = currentState.email,
                editPhone = currentState.phone,
                editLocation = currentState.location,
                editStudy = currentState.study
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
    fun onEditEmailChange(newEmail: String) {
        _uiState.update { it.copy(editEmail = newEmail) }
    }
    fun onEditPhoneChange(newPhone: String) {
        _uiState.update { it.copy(editPhone = newPhone) }
    }
    fun onEditLocationChange(newLocation: String) {
        _uiState.update { it.copy(editLocation = newLocation) }
    }
    fun onEditStudyChange(newStudy: String) {
        _uiState.update { it.copy(editStudy = newStudy) }
    }

    fun saveProfile() {
        _uiState.update { currentState ->
            val finalName = currentState.editName.ifBlank { currentState.name }
            val finalBio = currentState.editBio.ifBlank { currentState.bio }
            val finalEmail = currentState.editEmail.ifBlank { currentState.email }
            val finalPhone = currentState.editPhone.ifBlank { currentState.phone }
            val finalLocation = currentState.editLocation.ifBlank { currentState.location }
            val finalStudy = currentState.editStudy.ifBlank { currentState.study }
            
            currentState.copy(
                name = finalName,
                bio = finalBio,
                email = finalEmail,
                phone = finalPhone,
                location = finalLocation,
                study = finalStudy,
                initials = generateInitials(finalName),
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