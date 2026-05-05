package com.example.tugas6_1231401971

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugas6_1231401971.data.model.Article
import com.example.tugas6_1231401971.data.remote.HttpClientFactory
import com.example.tugas6_1231401971.data.repository.ArticleRepository
import com.example.tugas6_1231401971.ui.screen.DetailScreen
import com.example.tugas6_1231401971.ui.screen.NewsScreen
import com.example.tugas6_1231401971.ui.viewmodel.NewsViewModel


@Composable
fun App(newsApiKey: String = "") {
    val httpClient = remember { HttpClientFactory.create() }
    val repository = remember { ArticleRepository(httpClient, newsApiKey) }
    val viewModel  = viewModel { NewsViewModel(repository) }

    var selectedArticle by remember { mutableStateOf<Article?>(null) }
    if (selectedArticle != null) {
        DetailScreen(
            article = selectedArticle!!,
            onBack  = { selectedArticle = null }
        )

    } else {
        NewsScreen(
            viewModel      = viewModel,
            onArticleClick = { article ->
                selectedArticle = article
            }
        )
    }
}