package com.example.chessclock

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform