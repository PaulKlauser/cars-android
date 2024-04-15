package com.paulklauser.cars.trimdetail

import java.text.NumberFormat
import java.util.Locale

data class TrimDetail(
    val year: String,
    val make: String,
    val model: String,
    val description: String,
    val msrp: String,
    val fuelEconomy: FuelEconomy?,
    val horsepower: String,
    val torque: String
) {
    companion object {
        fun fromTrimDetailResponse(trimDetailResponse: TrimDetailResponse): TrimDetail {
            return TrimDetail(
                year = trimDetailResponse.year.toString(),
                make = trimDetailResponse.makeModel.make.name,
                model = trimDetailResponse.makeModel.name,
                description = trimDetailResponse.description,
                msrp = formatMsrp(trimDetailResponse.msrpDollars),
                fuelEconomy = resolveFuelEconomy(trimDetailResponse),
                horsepower = "${trimDetailResponse.makeModelTrimEngine.horsepowerHp}",
                torque = "${trimDetailResponse.makeModelTrimEngine.torqueFtLbs}"
            )
        }

        private fun formatMsrp(msrpDollars: Int): String {
            return NumberFormat.getCurrencyInstance(Locale.US).apply {
                maximumFractionDigits = 0
            }.format(msrpDollars)
        }

        private fun resolveFuelEconomy(trimDetailResponse: TrimDetailResponse): FuelEconomy? {
            return if (trimDetailResponse.makeModelTrimMileage.combinedMpg == null ||
                trimDetailResponse.makeModelTrimMileage.epaCityMpg == null ||
                trimDetailResponse.makeModelTrimMileage.epaHighwayMpg == null
            ) {
                null
            } else {
                FuelEconomy(
                    combinedMpg = trimDetailResponse.makeModelTrimMileage.combinedMpg.toString(),
                    cityMpg = trimDetailResponse.makeModelTrimMileage.epaCityMpg.toString(),
                    highwayMpg = trimDetailResponse.makeModelTrimMileage.epaHighwayMpg.toString()
                )
            }
        }
    }

    data class FuelEconomy(
        val combinedMpg: String,
        val cityMpg: String,
        val highwayMpg: String
    )
}
