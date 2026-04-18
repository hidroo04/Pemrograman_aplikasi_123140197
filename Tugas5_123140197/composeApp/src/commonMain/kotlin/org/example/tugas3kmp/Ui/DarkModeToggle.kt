package org.example.tugas3kmp.ui

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
    val cardColor = if (isDarkMode) Color(0xFF2C2C2C) else Color.White
    val textColor = if (isDarkMode) Color.White else Color(0xFF1C1B1F)
    val subColor  = if (isDarkMode) Color(0xFFAAAAAA) else Color(0xFF9E9E9E)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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