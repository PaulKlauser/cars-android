package com.paulklauser.cars.modeldetail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrimResponse(
    val year: Int,
    val name: String,
    val description: String,
    @SerialName("msrp")
    val msrpDollars: Int
)