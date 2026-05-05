package org.example.tugas3kmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6650A4),
                        Color(0xFF9C27B0),
                        Color(0xFFFF4081)
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        ProfileHeader(
            name = "Muhammad Rafiq R",
            bio = "Seorang mahasiswa Informatika semester 6 yang sedang mengambil matakuliah PAM\nInstitut Teknologi Sumatera",
            initials = "MR.R"
        )

        ProfileCard {
            InfoItem(icon = "📧", label = "Email", value = "muhammad.123140197@itera.ac.id")
            InfoItem(icon = "📱", label = "Phone", value = "+62 895-6098-15247")
            InfoItem(icon = "📍", label = "Location", value = "Bandar Lampung, Indonesia")
            InfoItem(icon = "🎓", label = "Study", value = "Teknik Informatika - ITERA")
        }

        ActionButton(
            text = "Edit Profile",
            onClick = {
                println("Edit Profile diklik!")
            }
        )


        ActionButton(
            text = "Follow",
            isPrimary = false, // Tampilan outline (secondary)
            onClick = {
                println("Follow diklik!")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}