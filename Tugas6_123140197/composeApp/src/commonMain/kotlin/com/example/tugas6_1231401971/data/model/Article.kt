package com.example.tugas6_1231401971.data.model

import kotlinx.serialization.Serializable

/**
 * Model for NewsAPI.org response.
 */
@Serializable
data class NewsResponse(
    val status: String = "",
    val totalResults: Int = 0,
    val articles: List<Article> = emptyList()
)

@Serializable
data class Article(
    val title: String = "",
    val url: String = "",
    val publishedAt: String? = null,
    val description: String? = null,
    val urlToImage: String? = null
)
