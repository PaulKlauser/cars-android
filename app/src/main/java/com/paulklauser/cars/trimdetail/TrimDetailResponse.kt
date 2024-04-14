package com.paulklauser.cars.trimdetail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrimDetailResponse(
    val year: Int,
    val name: String,
    val description: String,
    @SerialName("msrp")
    val msrpDollars: Int
)