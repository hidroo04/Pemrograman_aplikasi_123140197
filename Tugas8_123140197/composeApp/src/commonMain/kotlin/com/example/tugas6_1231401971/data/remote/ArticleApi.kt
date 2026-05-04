package com.example.tugas6_1231401971.data.remote

import com.example.tugas6_1231401971.data.model.Article
import com.example.tugas6_1231401971.data.model.CreateArticleRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class ArticleApi(private val client: HttpClient) {

    private val baseUrl = "https://jsonplaceholder.typicode.com"

    suspend fun getAllArticles(): List<Article> {
        return client
            .get("$baseUrl/posts")   // kirim GET request
            .body()                  // ubah JSON response → List<Article>
    }

    suspend fun getArticleById(id: Int): Article {
        return client
            .get("$baseUrl/posts/$id")
            .body()
    }

    suspend fun createArticle(title: String, body: String): Article {
        return client
            .post("$baseUrl/posts") {
                contentType(ContentType.Application.Json)   // beritahu server kita kirim JSON
                setBody(CreateArticleRequest(title = title, body = body))
            }
            .body()
    }

    suspend fun deleteArticle(id: Int): Boolean {
        val response: HttpResponse = client.delete("$baseUrl/posts/$id")
        return response.status.isSuccess()   // true jika status 200–299
    }
}