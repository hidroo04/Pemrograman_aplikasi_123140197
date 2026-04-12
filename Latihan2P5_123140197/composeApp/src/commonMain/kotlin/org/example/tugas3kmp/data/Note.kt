package org.example.tugas3kmp.data
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val category: String = "Umum"
)

/**
 * Data dummy untuk ditampilkan di NoteListScreen.
 * Di aplikasi nyata, data ini bisa dari database atau API.
 */
val dummyNotes = listOf(
    Note(1, "Belajar Compose", "Compose adalah UI framework deklaratif dari JetBrains untuk Kotlin Multiplatform.", "Kuliah"),
    Note(2, "MVVM Pattern", "Model-View-ViewModel memisahkan logika bisnis dari tampilan UI.", "Kuliah"),
    Note(3, "StateFlow", "StateFlow adalah state holder observable yang memancarkan update state terkini.", "Kuliah"),
    Note(4, "Navigasi KMP", "Navigation Compose memungkinkan perpindahan antar layar dengan NavController.", "Kuliah"),
    Note(5, "Tugas PAM", "Selesaikan tugas praktikum minggu ini sebelum deadline.", "Tugas")
)