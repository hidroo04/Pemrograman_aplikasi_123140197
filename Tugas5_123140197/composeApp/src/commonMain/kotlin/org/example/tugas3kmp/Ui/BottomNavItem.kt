package org.example.tugas3kmp.ui


sealed class BottomNavItem(
    val route: String,
    val icon: String,
    val label: String
) {
    object Notes     : BottomNavItem("note_list",  "📋", "Notes")
    object Favorites : BottomNavItem("favorites",  "⭐", "Favorites")
    object Profile   : BottomNavItem("profile",    "👤", "Profile")
}