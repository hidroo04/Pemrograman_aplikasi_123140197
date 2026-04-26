package com.example.tugas6_1231401971.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugas6_1231401971.data.model.Article
import com.example.tugas6_1231401971.util.UiState

@Composable
fun CreateArticleDialog(
    createState : UiState<Article>?,
    onDismiss   : () -> Unit,
    onSubmit    : (title: String, body: String) -> Unit
) {
    // State lokal form – hanya berlaku selama dialog terbuka
    var title      by remember { mutableStateOf("") }
    var body       by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf("") }
    var bodyError  by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("Tulis Artikel Baru")
                Text(
                    text  = "POST /posts",
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            }
        },

        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                // Input judul artikel
                OutlinedTextField(
                    value         = title,
                    onValueChange = { title = it; titleError = "" },
                    label         = { Text("Judul") },
                    isError       = titleError.isNotEmpty(),
                    supportingText = if (titleError.isNotEmpty()) {
                        { Text(titleError) }
                    } else null,
                    singleLine    = true
                )

                // Input isi artikel
                OutlinedTextField(
                    value         = body,
                    onValueChange = { body = it; bodyError = "" },
                    label         = { Text("Isi Artikel") },
                    isError       = bodyError.isNotEmpty(),
                    supportingText = if (bodyError.isNotEmpty()) {
                        { Text(bodyError) }
                    } else null,
                    minLines      = 4,
                    maxLines      = 6
                )
            }
        },

        // Tombol kirim POST request
        confirmButton = {
            Button(
                enabled = createState !is UiState.Loading,
                onClick = {
                    // Validasi input sebelum kirim
                    var valid = true
                    if (title.isBlank()) {
                        titleError = "Judul tidak boleh kosong"
                        valid = false
                    }
                    if (body.isBlank()) {
                        bodyError = "Isi tidak boleh kosong"
                        valid = false
                    }
                    if (valid) onSubmit(title, body)
                }
            ) {
                if (createState is UiState.Loading) {
                    // Tampilkan spinner kecil saat sedang loading
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                } else {
                    Text("Kirim (POST)")
                }
            }
        },

        // Tombol batal
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}