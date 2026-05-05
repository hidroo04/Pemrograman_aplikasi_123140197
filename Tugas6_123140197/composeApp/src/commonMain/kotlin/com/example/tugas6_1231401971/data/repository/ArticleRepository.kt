package com.example.tugas6_1231401971.data.repository

import com.example.tugas6_1231401971.data.model.Article
import com.example.tugas6_1231401971.data.remote.ArticleApi
import io.ktor.client.HttpClient

class ArticleRepository(client: HttpClient, private val apiKey: String) {

    private val api = ArticleApi(client)

    suspend fun getLatestNews(): Result<List<Article>> {
        return try {
            val response = api.getLatestNews(apiKey)
            if (response.status == "ok") {
                Result.success(response.articles)
            } else {
                Result.failure(Exception("Gagal mengambil data berita: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
