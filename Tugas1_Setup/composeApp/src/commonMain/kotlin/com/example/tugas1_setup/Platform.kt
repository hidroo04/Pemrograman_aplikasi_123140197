package com.example.tugas1_setup

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform