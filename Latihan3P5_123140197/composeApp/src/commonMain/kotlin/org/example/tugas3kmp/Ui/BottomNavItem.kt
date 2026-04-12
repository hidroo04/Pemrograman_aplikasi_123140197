package org.example.tugas3kmp.ui

sealed class BottomNavItem(
    val route: String,
    val icon: String,   // emoji sebagai icon
    val label: String
) {
    object Home      : BottomNavItem("home",      "🏠", "Home")
    object Notes     : BottomNavItem("note_list", "📋", "Catatan")
    object Profile   : BottomNavItem("profile",   "👤", "Profil")
}