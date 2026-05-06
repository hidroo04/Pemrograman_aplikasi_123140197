# Dokumentasi Implementasi Profile Module (MVVM + Acrylic UI)

Repositori ini berisi implementasi modul profil menggunakan Jetpack Compose dengan arsitektur **MVVM (Model-View-ViewModel)**, fitur **Edit Profile** lengkap, dan tema **Acrylic Material3**.

---

## 1. Implementasi MVVM Pattern

Pola MVVM memisahkan logika bisnis dan data dari tampilan (UI).

### **ProfileUiState (Data Class)**
Menyediakan *single source of truth* untuk seluruh state di layar profil.
```kotlin
data class ProfileUiState(
    val name: String = "...",
    val bio: String = "...",
    // ... data lainnya
    val isDarkMode: Boolean = false,
    val isEditMode: Boolean = false,
    val editName: String = "",
    val editBio: String = ""
    // ... field edit lainnya
)
```

### **ProfileViewModel (StateFlow)**
Mengelola state menggunakan `StateFlow` agar UI bersifat reaktif terhadap perubahan data.
```kotlin
class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun saveProfile() {
        _uiState.update { currentState ->
            val finalName = currentState.editName.ifBlank { currentState.name }
            currentState.copy(
                name = finalName,
                initials = generateInitials(finalName),
                isEditMode = false
            )
        }
    }
}
```

---

## 2. Fitur Edit Profile

Fitur ini memungkinkan pengguna mengubah data diri melalui form yang muncul secara kondisional.

### **State Hoisting untuk TextField**
Nilai dari `TextField` tidak disimpan di dalam Composable, melainkan ditarik ke atas (*hoisted*) ke ViewModel.
```kotlin
// Di ProfileScreen.kt
EditProfileForm(
    editName = uiState.editName,
    onNameChange = { viewModel.onEditNameChange(it) },
    onSave = { viewModel.saveProfile() },
    // ...
)
```

### **Form & Save Button**
Form menggunakan `OutlinedTextField` dan tombol "Simpan" yang memicu pembaruan di ViewModel.
```kotlin
OutlinedTextField(
    value = editName,
    onValueChange = onNameChange,
    label = { Text("Nama") }
)

Button(onClick = onSave) {
    Text("Simpan")
}
```

---

## 3. Fitur Dark Mode Toggle

Tema gelap/terang dikelola sepenuhnya oleh ViewModel dan mempengaruhi palet warna aplikasi secara global.

### **Switch State di ViewModel**
State `isDarkMode` disimpan di ViewModel dan dikonsumsi oleh `ProfileScreen` untuk menentukan warna latar belakang.
```kotlin
// Di DarkModeToggle.kt
Switch(
    checked = isDarkMode,
    onCheckedChange = { onToggle() } // Memanggil viewModel.toggleDarkMode()
)
```

### **Penerapan Tema Acrylic**
Menggunakan perpaduan warna transparan dan efek blur untuk menciptakan tampilan kaca.
```kotlin
val cardColor = if (isDarkMode) 
    Color.White.copy(alpha = 0.08f) 
else 
    Color.White.copy(alpha = 0.5f)

Card(
    colors = CardDefaults.cardColors(containerColor = cardColor),
    shape = RoundedCornerShape(24.dp),
    border = BorderStroke(1.dp, borderColor)
) { ... }
```

---

## Cara Penggunaan
1. Pastikan project menggunakan **Material3** dan **Jetpack Compose**.
2. Modul ini menggunakan `androidx.lifecycle.viewmodel.compose` untuk integrasi ViewModel.
3. Untuk melihat efek Acrylic secara maksimal, jalankan pada perangkat dengan **Android 12 (API 31)** atau lebih tinggi karena penggunaan `Modifier.blur()`.
