package com.example.tugas6_1231401971.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tugas6_1231401971.data.model.Article
import com.example.tugas6_1231401971.ui.component.ArticleCard
import com.example.tugas6_1231401971.ui.component.CreateArticleDialog
import com.example.tugas6_1231401971.ui.viewmodel.NewsViewModel
import com.example.tugas6_1231401971.util.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    onArticleClick: (Article) -> Unit   // callback saat artikel ditekan
) {

    val newsState   by viewModel.newsState.collectAsState()
    val createState by viewModel.createState.collectAsState()

    // State lokal untuk UI
    var showCreateDialog by remember { mutableStateOf(false) }
    var isRefreshing     by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(createState) {
        when (val state = createState) {
            is UiState.Success -> {
                snackbarHostState.showSnackbar("Artikel berhasil dibuat! ID: ${state.data.id}")
                viewModel.resetCreateState()
            }
            is UiState.Error -> {
                snackbarHostState.showSnackbar("Gagal: ${state.message}")
                viewModel.resetCreateState()
            }
            else -> { /* do nothing */ }
        }
    }

    // Reset isRefreshing saat newsState bukan Loading lagi
    LaunchedEffect(newsState) {
        if (newsState !is UiState.Loading) {
            isRefreshing = false
        }
    }

    // Scaffold = kerangka dasar halaman (TopBar + FAB + SnackBar + konten)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("News Reader")
                        Text(
                            text  = "Tugas Praktikum 6 – ITERA",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Text(
                    text  = "+",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh    = {
                isRefreshing = true
                viewModel.refresh()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            when (val state = newsState) {

                is UiState.Loading -> {
                    Box(
                        modifier          = Modifier.fillMaxSize(),
                        contentAlignment  = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator()
                            Text(
                                text  = "Mengambil data dari API...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // ── State 2: SUCCESS ─────────────────────────
                // Tampilkan daftar artikel di LazyColumn
                is UiState.Success -> {
                    LazyColumn(
                        contentPadding    = PaddingValues(
                            start  = 16.dp,
                            end    = 16.dp,
                            top    = 8.dp,
                            bottom = 80.dp   // ruang kosong di bawah untuk FAB
                        ),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Info jumlah artikel
                        item {
                            Text(
                                text  = "${state.data.size} artikel · GET /posts",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        // Render setiap artikel sebagai ArticleCard
                        // key = artikel.id agar LazyColumn bisa optimasi animasi
                        items(
                            items = state.data,
                            key   = { article -> article.id }
                        ) { article ->
                            ArticleCard(
                                article = article,
                                onClick = { onArticleClick(article) }
                            )
                        }
                    }
                }

                // ── State 3: ERROR ───────────────────────────
                // Tampilkan pesan error + tombol Retry
                is UiState.Error -> {
                    Box(
                        modifier         = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier            = Modifier
                                .fillMaxWidth()
                                .padding(32.dp)
                        ) {
                            Text(
                                text  = "⚠️",
                                style = MaterialTheme.typography.displaySmall
                            )

                            Text(
                                text  = "Gagal memuat artikel",
                                style = MaterialTheme.typography.titleMedium
                            )

                            // Pesan error dari exception
                            Text(
                                text  = state.message,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Tombol Retry – sesuai modul slide 26
                            Button(onClick = { viewModel.refresh() }) {
                                Text("Coba Lagi")
                            }
                        }
                    }
                }
            }
            // ══════════════════════════════════════════════════
        }
    }

    // Dialog untuk buat artikel baru (POST request)
    if (showCreateDialog) {
        CreateArticleDialog(
            createState = createState,
            onDismiss   = { showCreateDialog = false },
            onSubmit    = { title, body ->
                viewModel.createArticle(title, body)
                showCreateDialog = false
            }
        )
    }
}