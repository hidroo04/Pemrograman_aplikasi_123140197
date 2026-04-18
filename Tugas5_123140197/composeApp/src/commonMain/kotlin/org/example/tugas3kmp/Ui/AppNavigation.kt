package org.example.tugas3kmp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.tugas3kmp.viewmodel.NoteViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val noteViewModel = androidx.lifecycle.viewmodel.compose.viewModel<NoteViewModel>()

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Notes.route
    ) {
        composable(BottomNavItem.Notes.route) {
            NoteListScreen(
                noteViewModel = noteViewModel,
                onNoteClick = { noteId ->
                    navController.navigate(Screen.NoteDetail.createRoute(noteId))
                },
                onAddNote = {
                    navController.navigate(Screen.AddNote.route)
                },
                onEditNote = { noteId ->
                    navController.navigate(Screen.EditNote.createRoute(noteId))
                }
            )
        }

        composable(Screen.AddNote.route) {
            AddNoteScreen(
                noteViewModel = noteViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditNote.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            EditNoteScreen(
                noteId = noteId,
                noteViewModel = noteViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            NoteDetailScreen(
                noteId = noteId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}