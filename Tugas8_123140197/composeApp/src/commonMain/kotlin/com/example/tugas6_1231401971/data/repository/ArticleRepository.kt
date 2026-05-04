package com.example.tugas6_1231401971.data.repository

import com.example.tugas6_1231401971.data.model.Article
import com.example.tugas6_1231401971.data.remote.ArticleApi
import io.ktor.client.HttpClient

class ArticleRepository(client: HttpClient) {

    private val api = ArticleApi(client)

    suspend fun getArticles(): Result<List<Article>> {
        return try {
            val articles = api.getAllArticles().take(20)
            Result.success(articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getArticleById(id: Int): Result<Article> {
        return try {
            val article = api.getArticleById(id)
            Result.success(article)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createArticle(title: String, body: String): Result<Article> {
        return try {
            if (title.isBlank()) {
                return Result.failure(Exception("Judul tidak boleh kosong"))
            }
            if (body.isBlank()) {
                return Result.failure(Exception("Isi artikel tidak boleh kosong"))
            }

            val article = api.createArticle(title, body)
            Result.success(article)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteArticle(id: Int): Result<Boolean> {
        return try {
            val success = api.deleteArticle(id)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}