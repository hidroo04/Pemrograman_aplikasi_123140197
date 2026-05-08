package com.example.nutritionfoodanalysis.logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritionfoodanalysis.data.ai.GroqService
import com.example.nutritionfoodanalysis.data.api.RetrofitClient
import com.example.nutritionfoodanalysis.data.local.ChatDatabase
import com.example.nutritionfoodanalysis.data.local.ChatMessage
import com.example.nutritionfoodanalysis.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ChatRepository
    private val currentConversationId = UUID.randomUUID().toString()

    val messages: StateFlow<List<ChatMessage>>

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        val chatDao = ChatDatabase.getDatabase(application).chatDao()
        repository = ChatRepository(
            chatDao = chatDao,
            usdaApiService = RetrofitClient.usdaApiService,
            aiService = GroqService(RetrofitClient.groqApiService)
        )
        // Filter messages by current session ID
        messages = chatDao.getMessagesByConversation(currentConversationId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                repository.sendMessage(text, currentConversationId)
            } catch (e: Exception) {
                _errorMessage.value = "Koneksi bermasalah: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
