package com.paulklauser.cars.makes

import kotlinx.serialization.Serializable

@Serializable
data class MakesResponse(
    val data: List<ApiMake>
)