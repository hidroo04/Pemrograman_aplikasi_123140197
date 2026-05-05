package com.example.tugas6_1231401971.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.tugas6_1231401971.data.model.Article

/**
 * DetailScreen displays the details of a selected article.
 * Includes the main image, title, snippet, and a button to read the full article.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    article: Article,
    onBack: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val darkBlueBackground = Color(0xFF0D1B3E)

    Scaffold(
        containerColor = darkBlueBackground,
        topBar = {
            TopAppBar(
                title = { Text("Detail Berita", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", color = Color.White, fontSize = 24.sp, modifier = Modifier.padding(start = 8.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Main Image using 'thumbnail' from Article model
            AsyncImage(
                model = article.urlToImage ?: "",
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Category Label
                Text(
                    text = "CNN",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color(0xFFE91E63),
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Title
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 32.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Date using 'pubDate' from Article model
                Text(
                    text = article.publishedAt?.substringBefore("T") ?: "",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Description (Highlight) using 'description' from Article model
                Text(
                    text = article.description ?: "Tidak ada preview konten.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.LightGray,
                        lineHeight = 26.sp
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Button to open full article in web
                Button(
                    onClick = { uriHandler.openUri(article.url) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63)
                    )
                ) {
                    Text(
                        "Baca Berita Selengkapnya",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
