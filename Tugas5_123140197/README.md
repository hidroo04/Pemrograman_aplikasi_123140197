# 📱 Tugas Minggu 5 — Bottom Navigation & Full Navigation Flow
### Pengembangan Aplikasi Mobile | Institut Teknologi Sumatera

---

---

## 🗂️ Struktur Folder

```
commonMain/kotlin/org/example/tugas3kmp/
├── App.kt
├── data/
│   ├── Note.kt                 
│   └── ProfileData.kt
├── ui/
│   ├── AddNoteScreen.kt         
│   ├── EditNoteScreen.kt       
│   ├── FavoritesScreen.kt     
│   ├── NoteListScreen.kt       
│   ├── NoteDetailScreen.kt
│   ├── MainScreen.kt            
│   ├── AppNavigation.kt         
│   ├── BottomNavItem.kt        
│   ├── Screen.kt                
│   ├── ProfileScreen.kt
│   ├── ProfileHeader.kt
│   ├── ProfileCard.kt
│   ├── ActionButton.kt
│   ├── DarkModeToggle.kt
│   └── EditProfileForm.kt
└── viewmodel/
    ├── NoteViewModel.kt         
    └── ProfileViewModel.kt
```



## 🚀 Fitur-Fitur yang Digunakan

---

### 1. Bottom Navigation Bar (3 Tab)

Bottom Navigation memungkinkan pengguna berpindah antar tab utama
dengan satu ketukan. Tab yang aktif ditandai dengan warna ungu
dan indikator berbeda dari tab yang tidak aktif.

**File:** `ui/BottomNavItem.kt` dan `ui/MainScreen.kt`

```kotlin
// BottomNavItem.kt — definisi setiap tab
sealed class BottomNavItem(
    val route: String,
    val icon: String,
    val label: String
) {
    object Notes     : BottomNavItem("note_list", "📋", "Notes")
    object Favorites : BottomNavItem("favorites", "⭐", "Favorites")
    object Profile   : BottomNavItem("profile",   "👤", "Profile")
}
```

```kotlin
// MainScreen.kt — render NavigationBar
NavigationBar(containerColor = Color.White) {
    bottomNavItems.forEach { item ->
        NavigationBarItem(
            selected = currentRoute == item.route,
            onClick = {
                navController.navigate(item.route) {
                    popUpTo(BottomNavItem.Notes.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Text(item.icon, fontSize = 20.sp) },
            label = { Text(item.label, fontSize = 11.sp) }
        )
    }
}
```

**Cara kerja:**
- `selected = currentRoute == item.route` → highlight tab yang aktif
- `launchSingleTop = true` → tidak membuat instance baru jika tab sudah aktif
- `saveState / restoreState` → state tab tersimpan saat berpindah tab

---

### 2. currentBackStackEntry untuk Selected State

Digunakan untuk mendeteksi route aktif saat ini secara real-time.
Setiap kali navigasi berubah, `currentRoute` otomatis update
dan UI bottom nav ikut berubah.

**File:** `ui/MainScreen.kt`

```kotlin
// Observe route yang sedang aktif
val navBackStackEntry by navController.currentBackStackEntryAsState()
val currentRoute = navBackStackEntry?.destination?.route

// Digunakan untuk highlight tab aktif
selected = currentRoute == item.route

// Route yang menyembunyikan bottom nav
val hideBottomNavRoutes = listOf(
    Screen.NoteDetail.route,
    Screen.AddNote.route,
    Screen.EditNote.route
)

// Bottom nav hanya muncul di tab utama
if (currentRoute !in hideBottomNavRoutes) {
    NavigationBar { ... }
}
```

---

### 3. Floating Action Button → Navigate ke AddNoteScreen

FAB (tombol bulat + di pojok kanan bawah) digunakan untuk membuka
layar tambah catatan baru sebagai screen terpisah, bukan form inline.

**File:** `ui/NoteListScreen.kt`

```kotlin
// FAB navigate ke AddNoteScreen
floatingActionButton = {
    FloatingActionButton(
        onClick = onAddNote,
        containerColor = Color(0xFF6650A4)
    ) {
        Text("+", fontSize = 28.sp, color = Color.White)
    }
}
```

```kotlin
// MainScreen.kt — handle navigasi FAB
composable(BottomNavItem.Notes.route) {
    NoteListScreen(
        onAddNote = {
            navController.navigate(Screen.AddNote.route)
        },
        ...
    )
}
```

---

### 4. AddNoteScreen — Layar Tambah Catatan

Layar terpisah untuk menambah catatan baru dengan input judul
dan isi catatan. Ada validasi agar judul tidak boleh kosong.
Setelah simpan, otomatis kembali ke NoteListScreen.

**File:** `ui/AddNoteScreen.kt`

```kotlin
@Composable
fun AddNoteScreen(
    noteViewModel: NoteViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }

    // Input judul dengan validasi error
    OutlinedTextField(
        value = title,
        onValueChange = { title = it; titleError = false },
        label = { Text("Judul *") },
        isError = titleError,
        supportingText = {
            if (titleError) Text("Judul tidak boleh kosong", color = Color.Red)
        }
    )

    // Tombol simpan dengan validasi
    Button(
        onClick = {
            if (title.isBlank()) {
                titleError = true
            } else {
                noteViewModel.addNote(title, content)
                onNavigateBack()    // kembali ke list setelah simpan
            }
        }
    ) {
        Text("Simpan Catatan")
    }
}
```

---

### 5. EditNoteScreen — Passing noteId sebagai Argument

Edit catatan menggunakan argument navigasi. noteId dikirim
melalui URL route dan digunakan untuk mengambil data catatan
yang akan diedit dari ViewModel.

**File:** `ui/Screen.kt`, `ui/EditNoteScreen.kt`, `ui/MainScreen.kt`

```kotlin
// Screen.kt — definisi route dengan argument
object EditNote : Screen("edit_note/{noteId}") {
    fun createRoute(noteId: Int) = "edit_note/$noteId"
    // createRoute(3) → "edit_note/3"
}
```

```kotlin
// NoteListScreen.kt — tombol edit kirim noteId
OutlinedButton(onClick = { onEditNote(note.id) }) {
    Text("✏ Edit")
}
```

```kotlin
// MainScreen.kt — setup route dengan argument
composable(
    route = Screen.EditNote.route,
    arguments = listOf(
        navArgument("noteId") { type = NavType.IntType }
    )
) { backStackEntry ->
    val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
    EditNoteScreen(noteId = noteId, ...)
}
```

```kotlin
// EditNoteScreen.kt — load data berdasarkan noteId
@Composable
fun EditNoteScreen(noteId: Int, ...) {
    val note = noteViewModel.getNote(noteId)

    // Pre-fill form dengan data yang sudah ada
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }

    Button(
        onClick = {
            noteViewModel.updateNote(noteId, title, content)
            onNavigateBack()
        }
    ) {
        Text("Simpan Perubahan")
    }
}
```

---

### 6. Back Navigation dengan popBackStack()

Setiap screen non-tab memiliki tombol back di TopAppBar
yang memanggil `popBackStack()` untuk kembali ke layar sebelumnya.
Tombol back sistem di HP juga bekerja secara otomatis.

**File:** `ui/MainScreen.kt`

```kotlin
// Semua screen non-tab pakai popBackStack
composable(Screen.AddNote.route) {
    AddNoteScreen(
        onNavigateBack = { navController.popBackStack() }
    )
}

composable(Screen.EditNote.route, ...) { backStackEntry ->
    EditNoteScreen(
        noteId = ...,
        onNavigateBack = { navController.popBackStack() }
    )
}
```

```kotlin
// Di dalam screen — tombol back di TopAppBar
TopAppBar(
    navigationIcon = {
        TextButton(onClick = onNavigateBack) {
            Text("← Batal", color = Color.White)
        }
    }
)
```

---

### 7. FavoritesScreen — Filter Note Favorit

Tab Favorites menampilkan hanya catatan yang ditandai sebagai
favorit dengan menekan ikon bintang. Data di-filter langsung
dari StateFlow yang sama sehingga perubahan real-time.

**File:** `ui/FavoritesScreen.kt`, `viewmodel/NoteViewModel.kt`

```kotlin
// Note.kt — field isFavorite
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
)
```

```kotlin
// NoteViewModel.kt — toggle favorite
fun toggleFavorite(noteId: Int) {
    _notes.update { currentList ->
        currentList.toMutableList().apply {
            val index = indexOfFirst { it.id == noteId }
            if (index != -1) {
                set(index, get(index).copy(
                    isFavorite = !get(index).isFavorite
                ))
            }
        }
    }
}
```

```kotlin
// FavoritesScreen.kt — filter dan tampilkan
val allNotes by noteViewModel.notes.collectAsStateWithLifecycle()
val favoriteNotes = allNotes.filter { it.isFavorite }

if (favoriteNotes.isEmpty()) {
    // Tampilan kosong
    Text("Belum ada catatan favorit")
} else {
    LazyColumn {
        items(favoriteNotes) { note ->
            // Tampilkan item favorit
        }
    }
}
```

---

### 8. NoteViewModel — CRUD Operations

ViewModel sebagai pusat pengelolaan data catatan dengan
operasi Create, Read, Update, Delete menggunakan StateFlow.

**File:** `viewmodel/NoteViewModel.kt`

```kotlin
class NoteViewModel : ViewModel() {

    private val _notes = MutableStateFlow(dummyNotes.toMutableList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    // Read — ambil satu note berdasarkan ID
    fun getNote(noteId: Int): Note? =
        _notes.value.firstOrNull { it.id == noteId }

    // Create — tambah note baru
    fun addNote(title: String, content: String) {
        if (title.isBlank()) return
        val newNote = Note(id = nextId++, title = title, content = content)
        _notes.update { it.toMutableList().apply { add(newNote) } }
    }

    // Update — edit note yang ada
    fun updateNote(noteId: Int, newTitle: String, newContent: String) {
        _notes.update { list ->
            list.toMutableList().apply {
                val i = indexOfFirst { it.id == noteId }
                if (i != -1) set(i, get(i).copy(title = newTitle, content = newContent))
            }
        }
    }

    // Delete — hapus note
    fun deleteNote(noteId: Int) {
        _notes.update { it.toMutableList().apply { removeAll { n -> n.id == noteId } } }
    }
}
```

---

### 9. Shared ViewModel antar Tab

Semua screen berbagi satu instance NoteViewModel yang sama
agar perubahan data di satu screen langsung terlihat di screen lain.

**File:** `ui/MainScreen.kt`

```kotlin
@Composable
fun MainScreen() {
    // Satu instance ViewModel untuk semua screen
    val noteViewModel: NoteViewModel = viewModel()

    NavHost(...) {
        // Semua screen menerima noteViewModel yang sama
        composable(BottomNavItem.Notes.route) {
            NoteListScreen(noteViewModel = noteViewModel, ...)
        }
        composable(BottomNavItem.Favorites.route) {
            FavoritesScreen(noteViewModel = noteViewModel, ...)
        }
        composable(Screen.AddNote.route) {
            AddNoteScreen(noteViewModel = noteViewModel, ...)
        }
        composable(Screen.EditNote.route, ...) {
            EditNoteScreen(noteViewModel = noteViewModel, ...)
        }
    }
}
```

---

## 📸 Screenshot


---
