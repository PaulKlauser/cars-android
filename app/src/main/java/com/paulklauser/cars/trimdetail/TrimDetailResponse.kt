package com.paulklauser.cars.trimdetail

import com.paulklauser.cars.makes.ApiMake
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrimDetailResponse(
    val year: Int,
    val name: String,
    val description: String,
    @SerialName("msrp")
    val msrpDollars: Int,
    @SerialName("make_model")
    val makeModel: MakeModel
) {
    @Serializable
    data class MakeModel(
        val name: String,
        val make: ApiMake
    )
}