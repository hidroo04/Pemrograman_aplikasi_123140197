package org.example.tugas3kmp.viewmodel

import org.example.tugas3kmp.data.defaultProfile
data class ProfileUiState(
    val name: String = defaultProfile.name,
    val bio: String = defaultProfile.bio,
    val email: String = defaultProfile.email,
    val phone: String = defaultProfile.phone,
    val location: String = defaultProfile.location,
    val study: String = defaultProfile.study,
    val initials: String = defaultProfile.initials,
    val isDarkMode: Boolean = false,
    val isEditMode: Boolean = false,
    val editName: String = "",
    val editBio: String = "",
    val editEmail: String = "",
    val editPhone: String = "",
    val editLocation: String = "",
    val editStudy: String = ""
)
