package org.example.tugas3kmp.Ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
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

    val isDark = uiState.isDarkMode
    val bgColor = if (isDark) Color(0xFF1C1C1E) else Color(0xFFF2F2F7)
    val accentColor1 = if (isDark) Color(0xFF6650A4).copy(alpha = 0.3f) else Color(0xFFD1C4E9).copy(alpha = 0.4f)
    val accentColor2 = if (isDark) Color(0xFF311B92).copy(alpha = 0.2f) else Color(0xFFBBDEFB).copy(alpha = 0.3f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        // "Acrylic" Background Elements - Floating glowing circles
        Box(
            modifier = Modifier
                .offset(x = (-50).dp, y = 100.dp)
                .size(300.dp)
                .blur(80.dp)
                .clip(CircleShape)
                .background(accentColor1)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 50.dp, y = (-50).dp)
                .size(250.dp)
                .blur(100.dp)
                .clip(CircleShape)
                .background(accentColor2)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Dark Mode Toggle
            DarkModeToggle(
                isDarkMode = uiState.isDarkMode,
                onToggle = { viewModel.toggleDarkMode() }
            )

            // Header
            ProfileHeader(
                name = uiState.name,
                bio = uiState.bio,
                initials = uiState.initials,
                isDarkMode = uiState.isDarkMode
            )

            // Form edit
            if (uiState.isEditMode) {
                EditProfileForm(
                    editName = uiState.editName,
                    editBio = uiState.editBio,
                    editEmail = uiState.editEmail,
                    editPhone = uiState.editPhone,
                    editLocation = uiState.editLocation,
                    editStudy = uiState.editStudy,
                    onNameChange = { viewModel.onEditNameChange(it) },
                    onBioChange = { viewModel.onEditBioChange(it) },
                    onEmailChange = { viewModel.onEditEmailChange(it) },
                    onPhoneChange = { viewModel.onEditPhoneChange(it) },
                    onLocationChange = { viewModel.onEditLocationChange(it) },
                    onStudyChange = { viewModel.onEditStudyChange(it) },
                    onSave = { viewModel.saveProfile() },
                    onCancel = { viewModel.closeEditMode() },
                    isDarkMode = uiState.isDarkMode
                )
            }

            // Info kontak
            ProfileCard(isDarkMode = uiState.isDarkMode) {
                InfoItem(
                    icon = "📧",
                    label = "Email",
                    value = uiState.email,
                    isDarkMode = uiState.isDarkMode
                )
                InfoItem(
                    icon = "📱",
                    label = "Phone",
                    value = uiState.phone,
                    isDarkMode = uiState.isDarkMode
                )
                InfoItem(
                    icon = "📍",
                    label = "Location",
                    value = uiState.location,
                    isDarkMode = uiState.isDarkMode
                )
                InfoItem(
                    icon = "🎓",
                    label = "Study",
                    value = uiState.study,
                    isDarkMode = uiState.isDarkMode
                )
            }

            // Tombol Edit Profile
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
                onClick = { println("Follow diklik!") }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
