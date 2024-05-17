package com.paulklauser.cars.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform