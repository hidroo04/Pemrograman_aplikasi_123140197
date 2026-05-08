package com.example.nutritionfoodanalysis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutritionfoodanalysis.logic.ChatViewModel
import com.example.nutritionfoodanalysis.ui.screens.ChatScreen
import com.example.nutritionfoodanalysis.ui.screens.LandingScreen
import com.example.nutritionfoodanalysis.ui.theme.NutritionFoodAnalysisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutritionFoodAnalysisTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val chatViewModel: ChatViewModel = viewModel()

    NavHost(navController = navController, startDestination = "landing") {
        composable("landing") {
            LandingScreen(onStartChat = {
                navController.navigate("chat")
            })
        }
        composable("chat") {
            ChatScreen(
                viewModel = chatViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
