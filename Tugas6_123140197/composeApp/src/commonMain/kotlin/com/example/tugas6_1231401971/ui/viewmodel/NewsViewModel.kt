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

/**
 * ViewModel for the News Screen.
 * Manages the state of the news list and handles user interactions.
 */
class NewsViewModel(
    private val repository: ArticleRepository
) : ViewModel() {

    private val _newsState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val newsState: StateFlow<UiState<List<Article>>> = _newsState.asStateFlow()

    init {
        loadNews()
    }

    /**
     * Fetches the latest news from the repository and updates the UI state.
     */
    fun loadNews() {
        viewModelScope.launch {
            _newsState.value = UiState.Loading
            repository.getLatestNews()
                .onSuccess { articles ->
                    _newsState.value = UiState.Success(articles)
                }
                .onFailure { error ->
                    _newsState.value = UiState.Error(
                        error.message ?: "Gagal memuat berita. Periksa koneksi internet Anda."
                    )
                }
        }
    }

    /**
     * Triggers a refresh of the news list.
     */
    fun refresh() = loadNews()
}
