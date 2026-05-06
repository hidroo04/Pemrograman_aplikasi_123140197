package org.example.tugas3kmp.Ui


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DarkModeToggle(
    isDarkMode: Boolean,
    onToggle: () -> Unit
) {
    val cardColor = if (isDarkMode) Color.White.copy(alpha = 0.08f) else Color.White.copy(alpha = 0.5f)
    val borderColor = if (isDarkMode) Color.White.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.3f)
    val textColor = if (isDarkMode) Color.White else Color(0xFF1C1B1F)
    val subColor  = if (isDarkMode) Color(0xFFAAAAAA) else Color(0xFF9E9E9E)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon + teks
            Text(
                text = if (isDarkMode) "🌙" else "☀️",
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Dark Mode",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
                Text(
                    text = if (isDarkMode) "Mode gelap aktif" else "Mode terang aktif",
                    fontSize = 11.sp,
                    color = subColor
                )
            }

            // Switch — state dari ViewModel, klik lapor ke ViewModel
            Switch(
                checked = isDarkMode,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF6650A4),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFE0E0E0)
                )
            )
        }
    }
}