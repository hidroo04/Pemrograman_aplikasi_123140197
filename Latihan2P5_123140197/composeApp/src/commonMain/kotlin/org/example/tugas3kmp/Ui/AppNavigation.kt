package org.example.tugas3kmp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.tugas3kmp.ui.Screen
import org.example.tugas3kmp.ui.NoteListScreen
import org.example.tugas3kmp.ui.NoteDetailScreen



@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {

        composable(NavRoutes.HOME) {
            HomeScreen(
                onNavigateToDetail = {
                    navController.navigate(NavRoutes.DETAIL)
                },
                onNavigateToNoteList = {
                    navController.navigate(Screen.NoteList.route)
                }
            )
        }

        composable(NavRoutes.DETAIL) {
            DetailScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.NoteList.route) {
            NoteListScreen(
                onNoteClick = { noteId ->

                    navController.navigate(Screen.NoteDetail.createRoute(noteId))
                }
            )
        }

        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0

            NoteDetailScreen(
                noteId = noteId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}