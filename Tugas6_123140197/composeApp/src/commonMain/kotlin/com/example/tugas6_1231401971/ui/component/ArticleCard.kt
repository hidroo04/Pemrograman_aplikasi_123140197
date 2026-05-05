package com.example.tugas6_1231401971.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.tugas6_1231401971.data.model.Article

/**
 * A component to display a single news article in a list.
 * Designed based on the reference image with a dark theme style.
 */
@Composable
fun ArticleCard(
    article: Article,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        // News Image with rounded corners using thumbnail from Article model
        AsyncImage(
            model = article.urlToImage ?: "",
            contentDescription = article.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            // News Title
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Category and Time (placeholder as the API might not provide category)
            Row {
                Text(
                    text = "NEWS", // Placeholder category
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFFE91E63), // Pinkish color from reference
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "•  ${formatDate(article.publishedAt)}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.Gray
                    )
                )
            }
        }
    }
}

/**
 * Simple helper to format or trim the date string.
 */
private fun formatDate(dateStr: String?): String {
    if (dateStr == null) return ""
    // Input usually looks like: 2024-03-20T10:00:00.000Z
    // Extracting just the date part for simplicity
    return try {
        if (dateStr.contains("T")) {
            dateStr.substringBefore("T")
        } else {
            dateStr.take(10)
        }
    } catch (e: Exception) {
        dateStr
    }
}
