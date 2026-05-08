package com.example.nutritionfoodanalysis.data.ai

import com.example.nutritionfoodanalysis.BuildConfig
import com.example.nutritionfoodanalysis.data.api.GroqApiService
import com.example.nutritionfoodanalysis.data.api.GroqMessage
import com.example.nutritionfoodanalysis.data.api.GroqRequest
import com.example.nutritionfoodanalysis.data.local.ChatMessage

class GroqService(private val apiService: GroqApiService) {
    private val modelName = "gemma2-9b-it"
    private val token = "Bearer ${BuildConfig.GROQ_API_KEY}"

    suspend fun generateResponse(prompt: String, history: List<ChatMessage> = emptyList()): String {
        return try {
            val messages = mutableListOf<GroqMessage>()
            
            // System prompt
            messages.add(GroqMessage("system", "Anda adalah Noaii, asisten nutrisi profesional yang ramah. Berikan jawaban yang singkat, padat, dan informatif (maksimal 300 token). Gunakan Bahasa Indonesia."))
            
            // Condensed History (last 4 messages for context)
            if (history.isNotEmpty()) {
                val recentHistory = history.takeLast(4)
                recentHistory.forEach { 
                    messages.add(GroqMessage(if (it.isUser) "user" else "assistant", it.text))
                }
            }

            // Current prompt
            messages.add(GroqMessage("user", prompt))

            val request = GroqRequest(
                model = modelName,
                messages = messages,
                max_tokens = 300
            )

            val response = apiService.chatCompletion(token, request)
            response.choices.firstOrNull()?.message?.content?.trim() ?: "Maaf, Noaii sedang tidak bisa menjawab."
        } catch (e: Exception) {
            "Error AI: ${e.localizedMessage}"
        }
    }

    suspend fun analyzeNutrition(foodName: String, nutritionData: String): String {
        val prompt = "Analisis makanan '$foodName' dengan data USDA berikut secara singkat:\n$nutritionData"
        return generateResponse(prompt)
    }

    suspend fun summarizeHistory(history: List<ChatMessage>): String {
        if (history.isEmpty()) return ""
        val historyText = history.joinToString("\n") { "${if (it.isUser) "User" else "AI"}: ${it.text}" }
        val prompt = "Ringkas percakapan berikut dalam satu paragraf pendek agar saya ingat konteksnya:\n$historyText"
        
        return try {
            val request = GroqRequest(
                model = modelName,
                messages = listOf(GroqMessage("user", prompt)),
                max_tokens = 150
            )
            val response = apiService.chatCompletion(token, request)
            response.choices.firstOrNull()?.message?.content?.trim() ?: ""
        } catch (e: Exception) {
            ""
        }
    }
}
