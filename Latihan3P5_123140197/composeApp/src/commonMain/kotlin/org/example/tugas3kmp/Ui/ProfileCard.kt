package org.example.tugas3kmp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.HorizontalDivider

@Composable
fun ProfileCard(
    title: String = "Informasi Kontak",
    isDarkMode: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardColor = if (isDarkMode) Color(0xFF2C2C2C) else Color.White

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF6650A4)
            )
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun InfoItem(
    icon: String,
    label: String,
    value: String,
    isDarkMode: Boolean = false
) {
    val labelColor = if (isDarkMode) Color(0xFFAAAAAA) else Color(0xFF9E9E9E)
    val valueColor = if (isDarkMode) Color.White else Color(0xFF1C1B1F)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = icon, fontSize = 20.sp, modifier = Modifier.width(32.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 11.sp, color = labelColor, fontWeight = FontWeight.Medium)
            Text(text = value, fontSize = 14.sp, color = valueColor)
        }
    }
}