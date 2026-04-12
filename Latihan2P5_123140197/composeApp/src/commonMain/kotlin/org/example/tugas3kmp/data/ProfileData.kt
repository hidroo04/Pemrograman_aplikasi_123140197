package org.example.tugas3kmp.data
data class ProfileData(
    val name: String,
    val bio: String,
    val email: String,
    val phone: String,
    val location: String,
    val study: String,
    val initials: String
)

val defaultProfile = ProfileData(
    name = "Muhammad Rafiq R",
    bio = "Seorang mahasiswa Informatika semester 6\nInstitut Teknologi Sumatera",
    email = "muhammad.123140197@itera.ac.id",
    phone = "+62 895-6098-15247",
    location = "Bandar Lampung, Indonesia",
    study = "Teknik Informatika - ITERA",
    initials = "MR"
)