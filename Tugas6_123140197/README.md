# News App

## Implementasi Fitur Utama

### 1. Fetch Berita dari Public API (NewsAPI)
Aplikasi menggunakan **Ktor Client** sebagai library networking. Data diambil menggunakan endpoint `top-headlines` dan diautentikasi menggunakan API Key yang aman.

**Implementasi API (`ArticleApi.kt`):**
```kotlin
class ArticleApi(private val client: HttpClient) {
    private val baseUrl = "https://newsapi.org/v2/"

    suspend fun getLatestNews(apiKey: String, country: String = "us"): NewsResponse {
        return client.get("${baseUrl}top-headlines") {
            parameter("apiKey", apiKey)
            parameter("country", country)
        }.body()
    }
}
```

### 2. Repository Pattern
Pola Repository digunakan untuk mengabstraksi sumber data. Hal ini memisahkan logika bisnis dari implementasi networking, sehingga kode lebih modular.

**Implementasi Repository (`ArticleRepository.kt`):**
```kotlin
class ArticleRepository(client: HttpClient, private val apiKey: String) {
    private val api = ArticleApi(client)

    suspend fun getLatestNews(): Result<List<Article>> {
        return try {
            val response = api.getLatestNews(apiKey)
            if (response.status == "ok") {
                Result.success(response.articles)
            } else {
                Result.failure(Exception("Gagal mengambil data: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 3. Proper Loading, Success, dan Error States
Status UI dikelola secara eksplisit menggunakan `sealed class`. Hal ini memastikan aplikasi menangani setiap kondisi (loading, data tersedia, atau terjadi kesalahan) dengan baik.

**Model State (`UiState.kt`):**
```kotlin
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
```

### 4. Tampilan List Artikel
Daftar berita ditampilkan menggunakan `LazyColumn` untuk performa yang optimal. Setiap item menampilkan judul, deskripsi, dan gambar menggunakan library **Coil**.

**Komponen Card (`ArticleCard.kt`):**
```kotlin
@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Row(modifier = Modifier.clickable { onClick() }) {
        // Menampilkan Image dengan Coil
        AsyncImage(
            model = article.urlToImage,
            contentDescription = article.title,
            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(20.dp))
        )
        Column {
            Text(text = article.title, fontWeight = FontWeight.Bold)
            Text(text = "• ${article.publishedAt}")
        }
    }
}
```

### 5. Detail Screen
Saat item diklik, aplikasi menavigasi ke `DetailScreen` yang menampilkan konten lengkap artikel, gambar resolusi penuh, dan link untuk membaca berita selengkapnya di web browser.

### 6. Pull to Refresh Functionality
Aplikasi menggunakan `PullToRefreshBox` dari Material 3 untuk memungkinkan pengguna memperbarui berita dengan cara mengusap layar ke bawah (swipe-to-refresh).

**Logika Refresh (`NewsScreen.kt`):**
```kotlin
PullToRefreshBox(
    isRefreshing = isRefreshing,
    onRefresh = {
        isRefreshing = true
        viewModel.refresh()
    }
) {
    // Konten LazyColumn berada di sini
}
```

## Keamanan API Key
Untuk menjaga keamanan, API Key **tidak disimpan langsung di dalam kode** (hardcoded). Sebaliknya, key disimpan di dalam file `local.properties` yang diabaikan oleh Git.

**Langkah Konfigurasi:**
1. Tambahkan `NEWS_API_KEY=your_key` di `local.properties`.
2. Gradle akan membaca key tersebut dan menyediakannya melalui `BuildConfig.NEWS_API_KEY`.
3. Key kemudian diinjeksi dari platform Android ke common code saat aplikasi dimulai.
