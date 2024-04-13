package com.paulklauser.cars.models

import kotlinx.serialization.Serializable

@Serializable
data class ModelsResponse(
    val data: List<ApiModel>
) {
    @Serializable
    data class ApiModel(
        val id: Int,
        val name: String
    )
}
