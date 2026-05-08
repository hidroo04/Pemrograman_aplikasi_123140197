package com.example.nutritionfoodanalysis.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutritionfoodanalysis.R
import com.example.nutritionfoodanalysis.ui.theme.*

@Composable
fun LandingScreen(onStartChat: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(LiquidDarkTeal, LiquidBlack)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hello\nI'm Noaii",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = 44.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Surface(
                modifier = Modifier.size(180.dp),
                shape = CircleShape,
                color = LiquidCyan.copy(alpha = 0.1f),
                border = BorderStroke(2.dp, LiquidCyan.copy(alpha = 0.3f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Surface(
                        modifier = Modifier.size(140.dp),
                        shape = CircleShape,
                        color = LiquidCyan.copy(alpha = 0.2f)
                    ) {
                       Box(contentAlignment = Alignment.Center) {
                           Icon(
                               painter = painterResource(id = R.drawable.ic_launcher_foreground),
                               contentDescription = "Bot",
                               tint = LiquidCyan,
                               modifier = Modifier.size(100.dp)
                           )
                       }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Text(
                text = "How can I help\nyou?",
                color = Color.White,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            Button(
                onClick = onStartChat,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LiquidCyan),
                shape = RoundedCornerShape(30.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "I want to know!",
                    color = LiquidBlack,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    NutritionFoodAnalysisTheme {
        LandingScreen(onStartChat = {})
    }
}
