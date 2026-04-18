package org.example.tugas3kmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.tugas3kmp.viewmodel.NoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    noteViewModel: NoteViewModel = viewModel(),
    onNoteClick: (Int) -> Unit
) {
    val allNotes by noteViewModel.notes.collectAsStateWithLifecycle()

    // Filter hanya note favorite
    val favoriteNotes = allNotes.filter { it.isFavorite }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("⭐ Favorit", fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD97706),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        if (favoriteNotes.isEmpty()) {
            // Tampilan kosong
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("⭐", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Belum ada catatan favorit",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF9E9E9E)
                    )
                    Text(
                        text = "Tap ⭐ pada catatan\nuntuk menambahkan ke favorit",
                        fontSize = 13.sp,
                        color = Color(0xFFBBBBBB),
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(favoriteNotes, key = { it.id }) { note ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        onClick = { onNoteClick(note.id) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("⭐", fontSize = 20.sp, modifier = Modifier.padding(end = 10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(note.title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1C1B1F))
                                Text(note.content, fontSize = 12.sp, color = Color(0xFF9E9E9E), maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(note.category, fontSize = 11.sp, color = Color(0xFFD97706), fontWeight = FontWeight.Medium)
                            }
                            Text("›", fontSize = 20.sp, color = Color(0xFFD97706), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}