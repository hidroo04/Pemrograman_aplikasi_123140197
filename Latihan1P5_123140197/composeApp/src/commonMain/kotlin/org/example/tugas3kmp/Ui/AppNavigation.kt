package org.example.tugas3kmp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    // NavHost = wadah untuk semua layar yang bisa dinavigasi
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME   // layar pertama yang ditampilkan
    ) {

        // Daftarkan layar HOME
        composable(NavRoutes.HOME) {
            HomeScreen(
                onNavigateToDetail = {
                    navController.navigate(NavRoutes.DETAIL)
                }
            )
        }

        // Daftarkan layar DETAIL
        composable(NavRoutes.DETAIL) {
            DetailScreen(
                // popBackStack() = kembali ke layar sebelumnya
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}