package com.example.nutritionfoodanalysis.data.api

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GroqApiService {
    @POST("v1/chat/completions")
    suspend fun chatCompletion(
        @Header("Authorization") token: String,
        @Body request: GroqRequest
    ): GroqResponse
}

data class GroqRequest(
    val model: String,
    val messages: List<GroqMessage>,
    val max_tokens: Int = 300,
    val temperature: Double = 0.7
)

data class GroqMessage(
    val role: String,
    val content: String
)

data class GroqResponse(
    val choices: List<GroqChoice>
)

data class GroqChoice(
    val message: GroqMessage
)
