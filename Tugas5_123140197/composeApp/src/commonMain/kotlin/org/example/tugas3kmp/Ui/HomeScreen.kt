package org.example.tugas3kmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNavigateToDetail: () -> Unit,
    onNavigateToNoteList: () -> Unit    // ← TAMBAHAN
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // ── Avatar ────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFF6650A4)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MR",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Nama ──────────────────────────────────────────────────
        Text(
            text = "Muhammad Rafiq R",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1B1F)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── Bio singkat ───────────────────────────────────────────
        Text(
            text = "Mahasiswa Teknik Informatika\nInstitut Teknologi Sumatera",
            fontSize = 14.sp,
            color = Color(0xFF49454F),
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol ke Detail Profil
        Button(
            onClick = onNavigateToDetail,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6650A4)
            )
        ) {
            Text(
                text = "Lihat Detail Profil →",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Tombol ke Daftar Catatan ← TAMBAHAN
        OutlinedButton(
            onClick = onNavigateToNoteList,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF6650A4)
            )
        ) {
            Text(
                text = "Lihat Catatan →",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}