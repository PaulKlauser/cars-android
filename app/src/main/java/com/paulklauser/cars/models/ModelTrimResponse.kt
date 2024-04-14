package com.paulklauser.cars.models

import kotlinx.serialization.Serializable

@Serializable
data class ModelTrimResponse(
    val data: List<ModelTrim>
) {
    @Serializable
    data class ModelTrim(
        val id: Int,
        val description: String
    )
}
