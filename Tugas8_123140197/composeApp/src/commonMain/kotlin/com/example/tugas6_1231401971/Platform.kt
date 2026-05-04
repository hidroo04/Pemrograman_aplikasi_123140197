package com.example.tugas6_1231401971

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform