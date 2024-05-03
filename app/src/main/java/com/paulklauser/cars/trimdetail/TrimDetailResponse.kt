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
    val makeModel: MakeModel,
    @SerialName("make_model_trim_mileage")
    val makeModelTrimMileage: MakeModelTrimMileage,
    @SerialName("make_model_trim_engine")
    val makeModelTrimEngine: MakeModelTrimEngine
) {
    @Serializable
    data class MakeModel(
        val name: String,
        val make: ApiMake
    )

    @Serializable
    data class MakeModelTrimMileage(
        @SerialName("combined_mpg")
        val combinedMpg: Int?,
        @SerialName("epa_city_mpg")
        val epaCityMpg: Int?,
        @SerialName("epa_highway_mpg")
        val epaHighwayMpg: Int?
    )

    @Serializable
    data class MakeModelTrimEngine(
        @SerialName("horsepower_hp")
        val horsepowerHp: Int,
        @SerialName("torque_ft_lbs")
        val torqueFtLbs: Int?
    )
}