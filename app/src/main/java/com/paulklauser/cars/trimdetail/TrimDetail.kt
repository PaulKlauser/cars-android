package com.paulklauser.cars.trimdetail

data class TrimDetail(
    val year: String,
    val make: String,
    val model: String,
    val description: String,
    val msrp: String
) {
    companion object {
        fun fromTrimDetailResponse(trimDetailResponse: TrimDetailResponse): TrimDetail {
            return TrimDetail(
                year = trimDetailResponse.year.toString(),
                make = trimDetailResponse.makeModel.make.name,
                model = trimDetailResponse.makeModel.name,
                description = trimDetailResponse.description,
                msrp = "$${trimDetailResponse.msrpDollars}"
            )
        }
    }
}
