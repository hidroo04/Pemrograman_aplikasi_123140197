package org.example.tugas3kmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.tugas3kmp.data.Note
import org.example.tugas3kmp.viewmodel.NoteViewModel

/**
 * ══════════════════════════════════════════
 * NoteListScreen.kt — UPDATED
 * ══════════════════════════════════════════
 *
 * Sekarang punya fitur:
 * - Tombol Edit per item → buka form edit
 * - Tombol Hapus per item → hapus dengan konfirmasi
 * - Tombol Tambah (FAB) → buka form tambah
 * - Form edit/tambah inline
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    noteViewModel: NoteViewModel = viewModel(),
    onNoteClick: (Int) -> Unit
) {
    val notes by noteViewModel.notes.collectAsStateWithLifecycle()
    val editingNote by noteViewModel.editingNote.collectAsStateWithLifecycle()

    // State untuk form tambah catatan baru
    var showAddForm by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }

    // State konfirmasi hapus
    var noteToDelete by remember { mutableStateOf<Note?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Catatan", fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6650A4),
                    titleContentColor = Color.White
                )
            )
        },
        // FAB untuk tambah catatan baru
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddForm = true },
                containerColor = Color(0xFF6650A4)
            ) {
                Text("+", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {

            // ── Form Tambah Catatan ───────────────────────────────
            if (showAddForm) {
                NoteFormCard(
                    title = "Tambah Catatan",
                    titleValue = newTitle,
                    contentValue = newContent,
                    onTitleChange = { newTitle = it },
                    onContentChange = { newContent = it },
                    onSave = {
                        noteViewModel.addNote(newTitle, newContent)
                        newTitle = ""
                        newContent = ""
                        showAddForm = false
                    },
                    onCancel = {
                        newTitle = ""
                        newContent = ""
                        showAddForm = false
                    }
                )
            }

            // ── Form Edit Catatan ─────────────────────────────────
            if (editingNote != null) {
                var editTitle by remember(editingNote) { mutableStateOf(editingNote!!.title) }
                var editContent by remember(editingNote) { mutableStateOf(editingNote!!.content) }

                NoteFormCard(
                    title = "Edit Catatan",
                    titleValue = editTitle,
                    contentValue = editContent,
                    onTitleChange = { editTitle = it },
                    onContentChange = { editContent = it },
                    onSave = {
                        noteViewModel.updateNote(editingNote!!.id, editTitle, editContent)
                    },
                    onCancel = { noteViewModel.cancelEdit() }
                )
            }

            // ── Daftar Catatan ────────────────────────────────────
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(notes, key = { it.id }) { note ->
                    NoteItemWithActions(
                        note = note,
                        onClick = { onNoteClick(note.id) },
                        onEdit = { noteViewModel.startEdit(note) },
                        onDelete = { noteToDelete = note }
                    )
                }
            }
        }
    }

    // ── Dialog Konfirmasi Hapus ───────────────────────────────────
    if (noteToDelete != null) {
        AlertDialog(
            onDismissRequest = { noteToDelete = null },
            title = { Text("Hapus Catatan") },
            text = { Text("Yakin ingin menghapus \"${noteToDelete!!.title}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        noteViewModel.deleteNote(noteToDelete!!.id)
                        noteToDelete = null
                    }
                ) {
                    Text("Hapus", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { noteToDelete = null }) {
                    Text("Batal")
                }
            }
        )
    }
}

/**
 * Item catatan dengan tombol Edit dan Hapus.
 */
@Composable
fun NoteItemWithActions(
    note: Note,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {

            // Baris atas: nomor + judul + konten
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Badge ID
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFEDE9FE), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("#${note.id}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6650A4))
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(note.title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1C1B1F))
                    Text(note.content, fontSize = 12.sp, color = Color(0xFF9E9E9E), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color(0xFFF0F0F0))
            Spacer(modifier = Modifier.height(4.dp))

            // Baris bawah: tombol aksi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Tombol Detail
                OutlinedButton(
                    onClick = onClick,
                    modifier = Modifier.weight(1f).height(34.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF6650A4))
                ) {
                    Text("Detail", fontSize = 12.sp)
                }

                // Tombol Edit
                OutlinedButton(
                    onClick = onEdit,
                    modifier = Modifier.weight(1f).height(34.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0284C7))
                ) {
                    Text("✏ Edit", fontSize = 12.sp)
                }

                // Tombol Hapus
                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.weight(1f).height(34.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626))
                ) {
                    Text("🗑 Hapus", fontSize = 12.sp)
                }
            }
        }
    }
}

/**
 * Form reusable untuk tambah/edit catatan.
 */
@Composable
fun NoteFormCard(
    title: String,
    titleValue: String,
    contentValue: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF6650A4))
            HorizontalDivider(color = Color(0xFFE0E0E0))

            OutlinedTextField(
                value = titleValue,
                onValueChange = onTitleChange,
                label = { Text("Judul") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            OutlinedTextField(
                value = contentValue,
                onValueChange = onContentChange,
                label = { Text("Isi Catatan") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) { Text("Batal") }

                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6650A4))
                ) { Text("Simpan", color = Color.White) }
            }
        }
    }
}