# MyNotes - Implementasi DI dan Testing
## Arsitektur & DI Setup

Aplikasi menggunakan **Koin DI** dengan pemisahan modul yang jelas untuk mendukung skalabilitas dan *testability*:

- **Data Module**: Mengelola instansiasi `NotesDatabase`, `NoteLocalDataSource`, dan `NoteRepository`.
- **ViewModel Module**: Mengelola lifecycle `NotesViewModel` dan `SettingsViewModel`.
- **Common Module**: Menggabungkan seluruh modul untuk diinisialisasi saat aplikasi berjalan.

```kotlin
// Modules.kt
val dataModule = module {
    single { NotesDatabase(get()) }
    singleOf(::NoteLocalDataSource)
    singleOf(::NoteRepository)
}

val viewModelModule = module {
    viewModelOf(::NotesViewModel)
}
```

---

## Implementasi Testing

Pengujian dilakukan menggunakan pola **AAA (Arrange, Act, Assert)** untuk menjamin kode yang bersih dan mudah dibaca.

### 1. Mocking dengan MockK
Digunakan untuk mengisolasi unit yang diuji dengan memalsukan perilaku dependensinya.
- **Repository**: Memmock `NoteLocalDataSource`.
- **ViewModel**: Memmock `NoteRepository`.

```kotlin
// Contoh Mocking di NoteRepositoryTest
private val localDataSource: NoteLocalDataSource = mockk()
// Perilaku palsu
coEvery { localDataSource.insertNote(any(), any()) } just Runs
```

### 2. Unit Test (NoteRepository)
Menguji logika bisnis pada layer repository dengan minimal 5 test cases (Create, Read, Update, Delete, Search).
- **Lokasi**: `composeApp/src/androidUnitTest/kotlin/.../data/repository/NoteRepositoryTest.kt`

### 3. ViewModel Test & Flow Test (Turbine)
Menguji logika UI dan emisi data reaktif (`StateFlow`) menggunakan library **Turbine**.
- **Lokasi**: `composeApp/src/androidUnitTest/kotlin/.../ui/viewmodel/NotesViewModelTest.kt`

```kotlin
// Flow Test dengan Turbine
viewModel.notes.test {
    assertEquals(emptyList(), awaitItem()) // Initial state
    assertEquals(expectedNotes, awaitItem()) // After data loaded
}
```

### 4. UI Test (Compose Test)
Menguji antarmuka pengguna secara otomatis menggunakan `ComposeTestRule` dan **Robolectric** untuk menjalankan tes UI di JVM (tanpa emulator).
- **Lokasi**: `composeApp/src/androidUnitTest/kotlin/.../ui/screen/NotesScreenTest.kt`

```kotlin
@Test
fun testEmptyStateIsDisplayed() {
    composeTestRule.setContent {
        KoinContext { NotesScreen(onSettingsClick = {}) }
    }
    composeTestRule.onNodeWithText("No notes found").assertIsDisplayed()
}
```

---

## Screenshot Hasil
<img width="1248" height="575" alt="Screenshot 2026-05-08 215540" src="https://github.com/user-attachments/assets/a51079fd-c009-45c1-ba51-2eeb31bdca04" />
<br>
<img width="1102" height="542" alt="image" src="https://github.com/user-attachments/assets/2e296f90-92d8-4475-9b66-75647abfc820" />
<br>
<img width="1058" height="520" alt="image" src="https://github.com/user-attachments/assets/8214495e-b921-48f1-a27e-86b8dd79102c" />

## Video Test
https://github.com/user-attachments/assets/fe320271-72b0-4897-827b-9538317bb817







