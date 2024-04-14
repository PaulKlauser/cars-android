package com.paulklauser.cars.modeldetail

data class ModelDetail(
    val year: String,
    val description: String,
    val msrp: String
) {
    companion object {
        fun fromTrimResponse(trimResponse: TrimResponse): ModelDetail {
            return ModelDetail(
                year = trimResponse.year.toString(),
                description = trimResponse.description,
                msrp = "$${trimResponse.msrpDollars}"
            )
        }
    }
}
