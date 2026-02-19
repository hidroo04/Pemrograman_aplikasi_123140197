package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class NewsItem(
    val id: Int,
    val title: String,
    val category: String
)

val daftarBerita = listOf(
    "Kotlin Multiplatform Resmi Stabil",
    "Timnas Indonesia Lolos Piala Dunia",
    "NASA Konfirmasi Air Cair di Mars",
    "Film Indonesia Raup 10 Juta Penonton",
    "Android 16 Hadir dengan Fitur Baru",
    "Ilmuwan ITERA Temukan Spesies Baru",
    "BTS Comeback dengan Album Baru",
    "DPR Sahkan UU Data Pribadi"
)

val daftarKategori = listOf("Teknologi", "Olahraga", "Sains", "Hiburan", "Politik")

fun newsFeedFlow(): Flow<NewsItem> = flow {
    var id = 1
    while (true) {
        emit(
            NewsItem(
                id = id++,
                title = daftarBerita.random(),
                category = daftarKategori.random()
            )
        )
        delay(2000L)
    }
}

@Composable
fun App() {
    var newsList by remember { mutableStateOf(listOf<NewsItem>()) }
    val readCountFlow = remember { MutableStateFlow(0) }
    val readCount by readCountFlow.collectAsState()
    var isStreaming by remember { mutableStateOf(false) }
    val readIds = remember { mutableSetOf<Int>() }
    val scope = rememberCoroutineScope()

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "News Feed Simulator",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Berita dibaca: $readCount",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (!isStreaming) {
                        isStreaming = true
                        scope.launch {
                            newsFeedFlow()
                                .catch { }
                                .collect { news ->
                                    newsList = listOf(news) + newsList
                                    if (newsList.size > 20) {
                                        newsList = newsList.take(20)
                                    }
                                }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isStreaming
            ) {
                Text(if (isStreaming) "🟢 Streaming aktif..." else "▶ Mulai Stream Berita")
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            if (newsList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tekan tombol untuk memulai",
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(newsList, key = { it.id }) { news ->
                        val sudahDibaca = news.id in readIds

                        Card(
                            onClick = {
                                if (readIds.add(news.id)) {
                                    readCountFlow.value++
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (sudahDibaca)
                                    MaterialTheme.colorScheme.surfaceVariant
                                else
                                    MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = news.category,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = news.title,
                                    fontSize = 14.sp,
                                    fontWeight = if (sudahDibaca) FontWeight.Normal
                                    else FontWeight.SemiBold
                                )

                                if (sudahDibaca) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "✓ Sudah dibaca",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}