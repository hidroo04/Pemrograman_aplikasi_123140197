package com.example.tugas6_1231401971.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.db.NoteEntity
import com.example.tugas6_1231401971.ui.component.NoteItem
import com.example.tugas6_1231401971.ui.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(viewModel: NotesViewModel) {
    val notes by viewModel.notes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<NoteEntity?>(null) }
    var isEditMode by remember { mutableStateOf(false) }

    val noteColors = listOf(
        Color(0xFFF06292), // Pink
        Color(0xFFBA68C8), // Purple
        Color(0xFF5C6BC0), // Dark Indigo
        Color(0xFF3949AB), // Navy Blue
        Color(0xFF283593)  // Deep Blue
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                tonalElevation = 12.dp,
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Edit Mode Button (Left of FAB)
                    IconButton(onClick = { isEditMode = true }) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Edit Mode",
                            tint = if (isEditMode) Color(0xFF00B0FF) else Color.Gray,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    // Large Spacer for centered FAB
                    Spacer(modifier = Modifier.width(80.dp))

                    // View Mode Button (Right of FAB)
                    IconButton(onClick = { isEditMode = false }) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "View Mode",
                            tint = if (!isEditMode) Color(0xFF00B0FF) else Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFF40C4FF),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .size(64.dp)
                    .offset(y = 56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note", modifier = Modifier.size(36.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    "My Notes",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Text(
                    "Your daily notes that reminds you",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Search Bar
                TextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    placeholder = { Text("Search", color = Color.LightGray) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.LightGray) },
                    shape = RoundedCornerShape(28.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        disabledContainerColor = Color(0xFFF5F5F5),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF40C4FF)
                    ),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Mode Title
                Text(
                    text = if (isEditMode) "Edit Mode (CRUD)" else "All Notes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = if (isEditMode) Color(0xFF00B0FF) else Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.width(40.dp).height(3.dp).background(Color(0xFF40C4FF)))
                
                Spacer(modifier = Modifier.height(16.dp))
            }

            // List of Notes
            if (notes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No notes found", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 100.dp, top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(notes) { index, note ->
                        val backgroundColor = noteColors[index % noteColors.size]
                        NoteItem(
                            note = note,
                            backgroundColor = backgroundColor,
                            isEditMode = isEditMode,
                            onToggleDone = { viewModel.toggleDone(note) },
                            onDelete = { viewModel.deleteNote(note.id) },
                            onNoteClick = { selectedNote = note }
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddNoteDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { title, content ->
                viewModel.addNote(title, content)
                showAddDialog = false
            }
        )
    }

    selectedNote?.let { note ->
        NoteDetailDialog(
            note = note,
            onDismiss = { selectedNote = null }
        )
    }
}

@Composable
fun NoteDetailDialog(
    note: NoteEntity,
    onDismiss: () -> Unit
) {
    val scrollState = rememberScrollState()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(note.title, fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                Text(note.content, fontSize = 16.sp)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = Color(0xFF40C4FF))
            }
        },
        containerColor = Color.White
    )
}

@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Note", fontWeight = FontWeight.Bold) },
        containerColor = Color.White,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFF40C4FF)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = { Text("Start typing...") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 5,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(title, content) },
                enabled = title.isNotBlank() && content.isNotBlank()
            ) {
                Text("Done", color = Color(0xFF40C4FF), fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color(0xFF40C4FF))
            }
        }
    )
}
