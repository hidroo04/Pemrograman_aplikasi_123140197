package com.example.nutritionfoodanalysis.data.gemini

import com.example.nutritionfoodanalysis.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

class GeminiService {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    suspend fun generateResponse(prompt: String): String {
        return try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Maaf, saya tidak bisa memberikan jawaban saat ini."
        } catch (e: Exception) {
            "Error: ${e.localizedMessage}"
        }
    }

    suspend fun analyzeNutrition(foodName: String, nutritionData: String): String {
        val prompt = """
            Anda adalah asisten nutrisi profesional. 
            Berikut adalah data nutrisi mentah untuk '$foodName' dari USDA:
            $nutritionData
            
            Berikan analisis singkat, ramah, dan informatif tentang makanan ini. 
            Sebutkan kalori, protein, lemak, dan karbohidrat jika tersedia. 
            Berikan saran apakah makanan ini sehat atau bagaimana cara menyeimbangkannya.
            Gunakan Bahasa Indonesia.
        """.trimIndent()
        
        return generateResponse(prompt)
    }
}
