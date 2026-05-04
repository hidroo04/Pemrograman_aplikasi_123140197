package com.example.tugas6_1231401971.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Article(
    val id: Int,

    @SerialName("userId")
    val userId: Int,

    val title: String,

    val body: String
) {

    val imageUrl: String
        get() = "https://picsum.photos/seed/$id/600/300"
}

@Serializable
data class CreateArticleRequest(
    val title: String,
    val body: String,

    @SerialName("userId")
    val userId: Int = 1
)