package org.example.tugas3kmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.tugas3kmp.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    noteId: Int,
    noteViewModel: NoteViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    // Load data note berdasarkan noteId yang diterima dari navigasi
    val note = noteViewModel.getNote(noteId)

    // Pre-fill form dengan data note yang ada
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    var titleError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Catatan", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) {
                        Text("← Batal", color = Color.White, fontWeight = FontWeight.Medium)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0284C7),  // biru untuk bedakan dari Add
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        if (note == null) {
            // Note tidak ditemukan
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text("Catatan tidak ditemukan.", color = Color.Red)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Badge noteId — bukti passing argument berhasil
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(modifier = Modifier.padding(10.dp)) {
                    Text("Mengedit Note ID: ", fontSize = 13.sp, color = Color(0xFF0284C7))
                    Text("$noteId", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0284C7))
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Edit Catatan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0284C7)
                    )
                    HorizontalDivider(color = Color(0xFFE0E0E0))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it; titleError = false },
                        label = { Text("Judul *") },
                        isError = titleError,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )


                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Isi Catatan") },
                        minLines = 5,
                        maxLines = 8,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            Button(
                onClick = {
                    if (title.isBlank()) {
                        titleError = true
                    } else {
                        noteViewModel.updateNote(noteId, title, content)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
            ) {
                Text("Simpan Perubahan", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
        }
    }
}