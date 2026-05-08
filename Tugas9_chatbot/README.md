# Noaii - Nutrition AI Chatbot

Noaii adalah aplikasi chatbot cerdas berbasis Android yang menggabungkan kemampuan **Groq AI (Llama 3.1 8B)** dan **USDA Food Database** untuk memberikan analisis nutrisi makanan.

## Fitur Utama

1.  **Liquid Glass UI**: Desain modern dengan estetika *glassmorphism*, menggunakan degradasi warna *teal-to-black* dan bubble chat transparan.
2.  **AI Conversation (Groq)**: Menggunakan model `llama-3.1-8b-instant` untuk respon yang sangat cepat, cerdas, dan hemat token.
3.  **Food Nutrition Analysis**: Integrasi dengan API USDA untuk mengambil data nutrisi mentah (kalori, protein, lemak, karbohidrat) yang kemudian dianalisis oleh AI.
4.  **Local Analysis Caching**: Hasil analisis makanan disimpan di database lokal (**Room**). Jika pengguna menanyakan makanan yang sama, Noaii akan menjawab secara instan tanpa memanggil API.
5.  **Smart Context Management**: Aplikasi meringkas riwayat percakapan agar AI tetap memiliki konteks tanpa menghabiskan kuota token.
6.  **Session-Based Chat**: Setiap kali aplikasi dibuka, percakapan dimulai dari awal untuk pengalaman yang bersih, sementara riwayat lama tetap tersimpan aman di database.

## Arsitektur & Teknologi

*   **UI Layer**: Jetpack Compose (Material 3) dengan kustomisasi *Liquid Glass*.
*   **Logic Layer**: ViewModel & Coroutines untuk pemrosesan asinkron yang responsif.
*   **Data Layer**:
    *   **Retrofit**: Komunikasi dengan API Groq dan USDA.
    *   **Room Database**: Penyimpanan pesan chat dan cache analisis makanan.
*   **AI Engine**: Groq Inference API (Llama 3.1 8B).

---

## Source Code Highlights

### 1. Penanganan Error Handling (ViewModel)
Kami menerapkan penanganan error yang komprehensif untuk memastikan aplikasi tidak crash saat terjadi gangguan jaringan atau limit API.

```kotlin
fun sendMessage(text: String) {
    if (text.isBlank()) return
    viewModelScope.launch {
        _isLoading.value = true
        _errorMessage.value = null // Reset error
        try {
            repository.sendMessage(text, currentConversationId)
        } catch (e: Exception) {
            // Menangkap berbagai error (Timeout, 404, 429, dll)
            _errorMessage.value = "Koneksi bermasalah: ${e.localizedMessage}"
        } finally {
            _isLoading.value = false // Berhenti loading dalam kondisi apapun
        }
    }
}
```

### 2. Logika Caching & Routing (Repository)
Fitur unggulan di mana Noaii "mengingat" jawaban sebelumnya untuk menghemat API.

```kotlin
private suspend fun handleFoodAnalysis(text: String): String {
    val foodName = extractFoodName(text)
    
    // 1. Cek Cache di Database Lokal (Room)
    val cachedResponse = chatDao.getFoodCache(foodName)
    if (cachedResponse != null) {
        return "${cachedResponse.analysisText}\n\n(Data dari memori Noaii)"
    }

    return try {
        // 2. Jika tidak ada, panggil USDA API
        val searchResponse = usdaApiService.searchFood(foodName, BuildConfig.USDA_API_KEY)
        if (searchResponse.foods.isNotEmpty()) {
            val food = searchResponse.foods.first()
            val nutritionInfo = formatNutritionData(food)
            
            // 3. Minta AI menganalisis data mentah USDA
            val analysis = aiService.analyzeNutrition(food.description, nutritionInfo)
            
            // 4. Simpan ke Cache untuk penggunaan masa depan
            chatDao.insertFoodCache(FoodAnalysisCache(foodName, analysis))
            analysis
        } else {
            "Maaf, Noaii tidak menemukan data untuk '$foodName'."
        }
    } catch (e: Exception) {
        "Gagal mengambil data: ${e.localizedMessage}"
    }
}
```

### 3. UI Responsif (Compose)
Menggunakan `collectAsState` untuk bereaksi terhadap perubahan data dan status loading secara *real-time*.

```kotlin
val isLoading by viewModel.isLoading.collectAsState()
val errorMessage by viewModel.errorMessage.collectAsState()

// Menampilkan Snackbar otomatis saat ada error
LaunchedEffect(errorMessage) {
    errorMessage?.let {
        snackbarHostState.showSnackbar(it)
        viewModel.clearError()
    }
}

// Menampilkan indikator bot sedang berpikir
if (isLoading) {
    item { TypingIndicator() }
}
```

---

## Cara Setup
1. Tambahkan API Keys di `local.properties`:
   ```properties
   GROQ_API_KEY=gsk_your_key_here
   USDA_API_KEY=your_usda_key_here
   ```
2. Build & Run menggunakan Android Studio.

## Screenshot Aplikasi

---

