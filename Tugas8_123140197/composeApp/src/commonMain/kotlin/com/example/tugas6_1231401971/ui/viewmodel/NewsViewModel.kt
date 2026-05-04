package com.example.tugas6_1231401971.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas6_1231401971.data.model.Article
import com.example.tugas6_1231401971.data.repository.ArticleRepository
import com.example.tugas6_1231401971.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class NewsViewModel(
    private val repository: ArticleRepository
) : ViewModel() {

    // ── State untuk daftar artikel ──────────────────────────
    // Awal = Loading karena kita langsung fetch di init {}
    private val _newsState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val newsState: StateFlow<UiState<List<Article>>> = _newsState.asStateFlow()

    // ── State untuk create artikel (POST) ───────────────────
    // null = tidak ada operasi POST yang sedang berjalan
    private val _createState = MutableStateFlow<UiState<Article>?>(null)
    val createState: StateFlow<UiState<Article>?> = _createState.asStateFlow()

    // ── Simpan semua artikel asli untuk kebutuhan filtering ─
    private var cachedArticles: List<Article> = emptyList()

    // Langsung muat artikel begitu ViewModel dibuat
    init {
        loadArticles()
    }


    fun loadArticles() {
        viewModelScope.launch {
            // Step 1: tampilkan loading spinner
            _newsState.value = UiState.Loading

            // Step 2: minta data ke repository
            repository.getArticles()
                .onSuccess { articles ->
                    // Step 3a: berhasil
                    cachedArticles = articles
                    _newsState.value = UiState.Success(articles)
                }
                .onFailure { error ->
                    // Step 3b: gagal (tidak ada internet, dll)
                    _newsState.value = UiState.Error(
                        error.message ?: "Gagal memuat artikel. Cek koneksi internet."
                    )
                }
        }
    }

    fun refresh() = loadArticles()


    fun createArticle(title: String, body: String) {
        viewModelScope.launch {
            _createState.value = UiState.Loading

            repository.createArticle(title, body)
                .onSuccess { newArticle ->
                    _createState.value = UiState.Success(newArticle)

                    val current = cachedArticles
                    cachedArticles = listOf(newArticle) + current
                    _newsState.value = UiState.Success(cachedArticles)
                }
                .onFailure { error ->
                    _createState.value = UiState.Error(
                        error.message ?: "Gagal membuat artikel"
                    )
                }
        }
    }

    fun deleteArticle(articleId: Int) {
        viewModelScope.launch {
            repository.deleteArticle(articleId)
                .onSuccess {
                    cachedArticles = cachedArticles.filter { it.id != articleId }
                    _newsState.value = UiState.Success(cachedArticles)
                }
                .onFailure {
                }
        }
    }

    fun resetCreateState() {
        _createState.value = null
    }
}