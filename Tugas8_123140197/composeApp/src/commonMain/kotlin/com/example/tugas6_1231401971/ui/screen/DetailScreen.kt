package com.example.tugas6_1231401971.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.tugas6_1231401971.data.model.Article

// ============================================================
// DetailScreen.kt
//
// Screen detail yang ditampilkan saat user menekan satu artikel
// di NewsScreen.
//
// Screen ini TIDAK melakukan fetch data baru ke API karena
// data artikel sudah dikirim dari NewsScreen melalui parameter.
// Ini adalah pendekatan yang efisien:
//   - Tidak perlu request jaringan tambahan
//   - Data sudah ada di memory dari list sebelumnya
//
// PARAMETER:
//   article → objek Article yang datanya akan ditampilkan
//   onBack  → aksi saat tombol ← ditekan (kembali ke list)
// ============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    article: Article,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Artikel") },
                navigationIcon = {
                    // Tombol kembali ke NewsScreen
                    // Menggunakan TextButton dengan simbol ← agar tidak
                    // butuh dependency material-icons-extended
                    TextButton(onClick = onBack) {
                        Text("← Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->

        // verticalScroll agar konten panjang bisa di-scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ── Gambar utama artikel ─────────────────────────
            AsyncImage(
                model              = article.imageUrl,
                contentDescription = "Gambar ${article.title}",
                modifier           = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale       = ContentScale.Crop
            )

            // ── Label sumber ─────────────────────────────────
            Text(
                text  = "📰  JSONPlaceholder · GET /posts/${article.id}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // ── Judul ────────────────────────────────────────
            Text(
                text  = article.title.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.headlineSmall
            )

            // ── Isi artikel ──────────────────────────────────
            Text(
                text  = article.body,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ── Info card API ────────────────────────────────
            // Menampilkan metadata request yang digunakan
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier            = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text  = "ℹ️  Info API Call",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(text = "Method   : GET", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Endpoint : /posts/${article.id}", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Base URL : jsonplaceholder.typicode.com", style = MaterialTheme.typography.bodySmall)
                    Text(text = "User ID  : ${article.userId}", style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}