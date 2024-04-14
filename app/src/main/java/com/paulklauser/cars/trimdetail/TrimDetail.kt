package com.paulklauser.cars.trimdetail

data class TrimDetail(
    val year: String,
    val description: String,
    val msrp: String
) {
    companion object {
        fun fromTrimDetailResponse(trimDetailResponse: TrimDetailResponse): TrimDetail {
            return TrimDetail(
                year = trimDetailResponse.year.toString(),
                description = trimDetailResponse.description,
                msrp = "$${trimDetailResponse.msrpDollars}"
            )
        }
    }
}
