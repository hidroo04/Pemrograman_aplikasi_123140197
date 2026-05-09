# Noaii - Nutrition AI Chatbot

Noaii adalah aplikasi chatbot cerdas berbasis Android yang menggabungkan kemampuan **Groq AI (Llama 3.1 8B)** dan **USDA Food Database** untuk memberikan analisis nutrisi makanan.

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

### landing Page
<img width="232" height="516" alt="image" src="https://github.com/user-attachments/assets/3740b8b6-0c1c-4499-9f7c-e7d04e46c58b" />

### Chat Page
<img width="232" height="516" alt="image" src="https://github.com/user-attachments/assets/31ec3fac-31b9-4bbc-968f-bd9ac05c1fa5" />
<br></br>
<img width="232" height="516" alt="image" src="https://github.com/user-attachments/assets/72254e7e-8a7a-455e-a34e-e037459a672d" />

### Video Dokumentasi
https://github.com/user-attachments/assets/9ce51cd3-b298-4809-987c-7baa6e3a3c8c






---

