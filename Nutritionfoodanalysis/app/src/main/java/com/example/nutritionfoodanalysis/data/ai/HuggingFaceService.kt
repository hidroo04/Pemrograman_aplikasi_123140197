package com.example.nutritionfoodanalysis.data.ai

import com.example.nutritionfoodanalysis.BuildConfig
import com.example.nutritionfoodanalysis.data.api.HuggingFaceApiService
import com.example.nutritionfoodanalysis.data.api.HuggingFaceRequest

class HuggingFaceService(private val apiService: HuggingFaceApiService) {
    private val modelUrl = "models/mistralai/Mistral-7B-Instruct-v0.2"
    private val token = "Bearer ${BuildConfig.HF_API_KEY}"

    suspend fun generateResponse(prompt: String): String {
        return try {
            val response = apiService.generateText(modelUrl, token, HuggingFaceRequest(prompt))
            response.firstOrNull()?.generated_text?.trim() ?: "Maaf, saya tidak bisa memberikan jawaban saat ini."
        } catch (e: Exception) {
            "Error AI: ${e.localizedMessage}"
        }
    }

    suspend fun analyzeNutrition(foodName: String, nutritionData: String): String {
        val prompt = "<s>[INST] Anda adalah asisten nutrisi profesional. " +
                "Berikut adalah data nutrisi mentah untuk '$foodName' dari USDA:\n" +
                "$nutritionData\n\n" +
                "Berikan analisis singkat, ramah, dan informatif tentang makanan ini. " +
                "Sebutkan kalori, protein, lemak, dan karbohidrat jika tersedia. " +
                "Berikan saran apakah makanan ini sehat atau bagaimana cara menyeimbangkannya. " +
                "Gunakan Bahasa Indonesia. [/INST]"
        
        return generateResponse(prompt)
    }
}
