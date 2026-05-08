package com.example.nutritionfoodanalysis.data.repository

import com.example.nutritionfoodanalysis.BuildConfig
import com.example.nutritionfoodanalysis.data.ai.GroqService
import com.example.nutritionfoodanalysis.data.api.UsdaApiService
import com.example.nutritionfoodanalysis.data.local.ChatDao
import com.example.nutritionfoodanalysis.data.local.ChatMessage
import com.example.nutritionfoodanalysis.data.local.FoodAnalysisCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ChatRepository(
    private val chatDao: ChatDao,
    private val usdaApiService: UsdaApiService,
    private val aiService: GroqService
) {
    val allMessages: Flow<List<ChatMessage>> = chatDao.getAllMessages()

    suspend fun sendMessage(text: String, conversationId: String = "default") {
        // Save user message
        chatDao.insertMessage(ChatMessage(text = text, isUser = true, conversationId = conversationId))

        // Get recent history for context
        val history = chatDao.getMessagesByConversation(conversationId).first()

        // Determine if it's a food analysis request
        val response = if (isFoodAnalysisRequest(text)) {
            handleFoodAnalysis(text)
        } else {
            aiService.generateResponse(text, history)
        }

        // Save bot response
        chatDao.insertMessage(ChatMessage(text = response, isUser = false, conversationId = conversationId))
    }

    private fun isFoodAnalysisRequest(text: String): Boolean {
        val keywords = listOf("analisis", "nutrisi", "kandungan", "berapa kalori", "sehatkah")
        return keywords.any { text.lowercase().contains(it) }
    }

    private suspend fun handleFoodAnalysis(text: String): String {
        val foodName = extractFoodName(text)
        
        // 1. Check Cache first
        val cachedResponse = chatDao.getFoodCache(foodName)
        if (cachedResponse != null) {
            return "${cachedResponse.analysisText}\n\n(Data dari memori Noaii)"
        }

        return try {
            val searchResponse = usdaApiService.searchFood(foodName, BuildConfig.USDA_API_KEY)
            if (searchResponse.foods.isNotEmpty()) {
                val food = searchResponse.foods.first()
                val nutritionInfo = food.foodNutrients.joinToString("\n") { 
                    "${it.nutrientName}: ${it.value} ${it.unitName}"
                }
                val analysis = aiService.analyzeNutrition(food.description, nutritionInfo)
                
                // 2. Save to Cache
                chatDao.insertFoodCache(FoodAnalysisCache(foodName, analysis))
                
                analysis
            } else {
                "Maaf, saya tidak menemukan informasi nutrisi untuk '$foodName' di database USDA."
            }
        } catch (e: Exception) {
            "Terjadi kesalahan saat mengambil data nutrisi: ${e.localizedMessage}"
        }
    }

    private fun extractFoodName(text: String): String {
        var foodName = text.lowercase()
        val keywords = listOf("analisis", "nutrisi", "kandungan", "berapa kalori", "sehatkah", "tolong", "cek")
        keywords.forEach { foodName = foodName.replace(it, "") }
        return foodName.trim()
    }
    
    suspend fun clearHistory() {
        chatDao.deleteAllMessages()
    }
}
