package org.example.tugas3kmp.ui

sealed class Screen(val route: String) {

    // Detail note — passing noteId
    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }

    // Tambah note baru — tidak ada argument
    object AddNote : Screen("add_note")

    // Edit note — passing noteId sebagai argument
    object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Int) = "edit_note/$noteId"
    }
}