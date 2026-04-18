package org.example.tugas3kmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.tugas3kmp.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val bgColor = if (uiState.isDarkMode) Color(0xFF121212) else Color(0xFFF5F5F5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        DarkModeToggle(
            isDarkMode = uiState.isDarkMode,
            onToggle = { viewModel.toggleDarkMode() }
        )

        ProfileHeader(
            name = uiState.name,
            bio = uiState.bio,
            initials = uiState.initials,
            isDarkMode = uiState.isDarkMode
        )

        if (uiState.isEditMode) {
            EditProfileForm(
                editName = uiState.editName,
                editBio = uiState.editBio,
                onNameChange = { viewModel.onEditNameChange(it) },
                onBioChange = { viewModel.onEditBioChange(it) },
                onSave = { viewModel.saveProfile() },
                onCancel = { viewModel.closeEditMode() },
                isDarkMode = uiState.isDarkMode
            )
        }

        ProfileCard(isDarkMode = uiState.isDarkMode) {
            InfoItem(icon = "📧", label = "Email", value = uiState.email, isDarkMode = uiState.isDarkMode)
            InfoItem(icon = "📱", label = "Phone", value = uiState.phone, isDarkMode = uiState.isDarkMode)
            InfoItem(icon = "📍", label = "Location", value = uiState.location, isDarkMode = uiState.isDarkMode)
            InfoItem(icon = "🎓", label = "Study", value = uiState.study, isDarkMode = uiState.isDarkMode)
        }

        ActionButton(
            text = if (uiState.isEditMode) "Tutup Edit" else "Edit Profile",
            onClick = {
                if (uiState.isEditMode) viewModel.closeEditMode()
                else viewModel.openEditMode()
            }
        )

        ActionButton(
            text = "Follow",
            isPrimary = false,
            onClick = { }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}