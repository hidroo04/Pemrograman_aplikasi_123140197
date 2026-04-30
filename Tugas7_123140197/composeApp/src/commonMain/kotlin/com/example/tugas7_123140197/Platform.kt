package com.example.tugas7_123140197

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform