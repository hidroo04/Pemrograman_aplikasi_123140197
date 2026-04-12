package org.example.tugas3kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform