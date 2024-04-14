package com.paulklauser.cars.models

data class Trim(
    val id: String,
    val description: String
) {
    companion object {
        fun fromApiTrim(apiTrim: ModelTrimResponse.ModelTrim): Trim {
            return Trim(
                apiTrim.id.toString(),
                apiTrim.description
            )
        }
    }
}