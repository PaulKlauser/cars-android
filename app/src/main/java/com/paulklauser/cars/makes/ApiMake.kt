package com.paulklauser.cars.makes

import kotlinx.serialization.Serializable

@Serializable
data class ApiMake(
    val id: Int,
    val name: String
)