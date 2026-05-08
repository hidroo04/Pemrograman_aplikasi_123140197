package com.example.nutritionfoodanalysis.logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutritionfoodanalysis.data.api.RetrofitClient
import com.example.nutritionfoodanalysis.data.gemini.GeminiService
import com.example.nutritionfoodanalysis.data.local.ChatDatabase
import com.example.nutritionfoodanalysis.data.local.ChatMessage
import com.example.nutritionfoodanalysis.data.repository.ChatRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ChatRepository

    val messages: StateFlow<List<ChatMessage>>

    init {
        val chatDao = ChatDatabase.getDatabase(application).chatDao()
        repository = ChatRepository(
            chatDao = chatDao,
            usdaApiService = RetrofitClient.usdaApiService,
            geminiService = GeminiService()
        )
        messages = repository.allMessages.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.sendMessage(text)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}
