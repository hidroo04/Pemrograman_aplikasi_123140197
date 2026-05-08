package com.example.nutritionfoodanalysis.data.repository

import com.example.nutritionfoodanalysis.BuildConfig
import com.example.nutritionfoodanalysis.data.api.UsdaApiService
import com.example.nutritionfoodanalysis.data.gemini.GeminiService
import com.example.nutritionfoodanalysis.data.local.ChatDao
import com.example.nutritionfoodanalysis.data.local.ChatMessage
import kotlinx.coroutines.flow.Flow

class ChatRepository(
    private val chatDao: ChatDao,
    private val usdaApiService: UsdaApiService,
    private val geminiService: GeminiService
) {
    val allMessages: Flow<List<ChatMessage>> = chatDao.getAllMessages()

    suspend fun sendMessage(text: String) {
        // Save user message
        chatDao.insertMessage(ChatMessage(text = text, isUser = true))

        // Determine if it's a food analysis request
        val response = if (isFoodAnalysisRequest(text)) {
            handleFoodAnalysis(text)
        } else {
            geminiService.generateResponse(text)
        }

        // Save bot response
        chatDao.insertMessage(ChatMessage(text = response, isUser = false))
    }

    private fun isFoodAnalysisRequest(text: String): Boolean {
        val keywords = listOf("analisis", "nutrisi", "kandungan", "berapa kalori", "sehatkah")
        return keywords.any { text.lowercase().contains(it) }
    }

    private suspend fun handleFoodAnalysis(text: String): String {
        // Simple extraction: assume the user mentions the food item
        // For better results, we could use Gemini to extract the food name first
        val foodName = extractFoodName(text)
        
        return try {
            val searchResponse = usdaApiService.searchFood(foodName, BuildConfig.USDA_API_KEY)
            if (searchResponse.foods.isNotEmpty()) {
                val food = searchResponse.foods.first()
                val nutritionInfo = food.foodNutrients.joinToString("\n") { 
                    "${it.nutrientName}: ${it.value} ${it.unitName}"
                }
                geminiService.analyzeNutrition(food.description, nutritionInfo)
            } else {
                "Maaf, saya tidak menemukan informasi nutrisi untuk '$foodName' di database USDA."
            }
        } catch (e: Exception) {
            "Terjadi kesalahan saat mengambil data nutrisi: ${e.localizedMessage}"
        }
    }

    private fun extractFoodName(text: String): String {
        // Remove keywords to try and isolate the food name
        var foodName = text.lowercase()
        val keywords = listOf("analisis", "nutrisi", "kandungan", "berapa kalori", "sehatkah", "tolong", "cek")
        keywords.forEach { foodName = foodName.replace(it, "") }
        return foodName.trim()
    }
    
    suspend fun clearHistory() {
        chatDao.deleteAllMessages()
    }
}
