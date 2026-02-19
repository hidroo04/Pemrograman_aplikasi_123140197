

## Tugas Pertemuan 2 — Pengembangan Aplikasi Mobile

---

## Cara Menjalankan
### 1. Membuat folder proyek
Buka [kotlin Multiplatform Wizard](https://kmp.jetbrains.com/?android=true&includeTests=true) untuk membuat folder proyek 

<img width="701" height="833" alt="image" src="https://github.com/user-attachments/assets/691cf242-a0d3-4b3d-8bd7-37f4c3d2a986" />

Kemudian klik download lalu ekstrak filenya, setelah di ekstrak buka Android studio, pilih project kemudian open, dan pilih file yang telah di ekstrak.


### 2. Menambahkan dependencies
Buka file Gradle Scripts/buid.gradle.kts( Module :composeApp) 

pada:

```kotlin
commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            ....

}
```
Tambahkan: 

```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
```

Lalu klik sync

### 3. Mengganti isi app.kt sesuai yang ada di soal
import modul:
```kotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
```
Kemudian ganti isi dengan:
```kotlin
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
```

Kemudian lakukan run, jika berhasil maka akan menampilkan seperti pada gambar:

<img width="392" height="871" alt="image" src="https://github.com/user-attachments/assets/8e137b5f-12d6-4382-b7d9-3cb486874357" />

