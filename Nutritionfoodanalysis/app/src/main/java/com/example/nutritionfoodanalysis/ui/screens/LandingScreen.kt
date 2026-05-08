package com.example.nutritionfoodanalysis.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutritionfoodanalysis.ui.theme.LandingBackground

@Composable
fun LandingScreen(onStartChat: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LandingBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello\nI'm Noaii",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Robot Icon Placeholder (Circular with a face)
        Surface(
            modifier = Modifier.size(150.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.2f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                // Outer circle
                Surface(
                    modifier = Modifier.size(110.dp),
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.3f)
                ) {
                   Box(contentAlignment = Alignment.Center) {
                       // Face
                       Text("🤖", fontSize = 60.sp)
                   }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = "How can I help\nyou?",
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(64.dp))
        
        Button(
            onClick = onStartChat,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "I want to know!",
                color = LandingBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
