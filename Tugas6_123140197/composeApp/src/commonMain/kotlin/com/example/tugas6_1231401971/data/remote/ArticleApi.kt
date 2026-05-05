package com.example.tugas6_1231401971.data.remote

import com.example.tugas6_1231401971.data.model.NewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ArticleApi(private val client: HttpClient) {

    private val baseUrl = "https://newsapi.org/v2/"

    /**
     * Fetch top headlines from NewsAPI.org.
     */
    suspend fun getLatestNews(apiKey: String, country: String = "us"): NewsResponse {
        return client
            .get("${baseUrl}top-headlines") {
                parameter("apiKey", apiKey)
                parameter("country", country)
            }
            .body()
    }
}
