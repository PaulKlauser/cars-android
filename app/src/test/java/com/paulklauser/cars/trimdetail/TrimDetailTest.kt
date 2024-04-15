package com.paulklauser.cars.trimdetail

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.junit.Test

class TrimDetailTest {
    @Test
    fun `null combined mpg returns null fuel economy`() {
        val trimDetailResponse = createTrimDetailResponse(
            makeModelTrimMileage = TrimDetailResponse.MakeModelTrimMileage(
                combinedMpg = null,
                epaCityMpg = 20,
                epaHighwayMpg = 30
            )
        )

        val trimDetail = TrimDetail.fromTrimDetailResponse(trimDetailResponse)

        assertThat(trimDetail).prop(TrimDetail::fuelEconomy).isEqualTo(null)
    }

    @Test
    fun `null city mpg returns null fuel economy`() {
        val trimDetailResponse = createTrimDetailResponse(
            makeModelTrimMileage = TrimDetailResponse.MakeModelTrimMileage(
                combinedMpg = 25,
                epaCityMpg = null,
                epaHighwayMpg = 30
            )
        )

        val trimDetail = TrimDetail.fromTrimDetailResponse(trimDetailResponse)

        assertThat(trimDetail).prop(TrimDetail::fuelEconomy).isEqualTo(null)
    }

    @Test
    fun `null highway mpg returns null fuel economy`() {
        val trimDetailResponse = createTrimDetailResponse(
            makeModelTrimMileage = TrimDetailResponse.MakeModelTrimMileage(
                combinedMpg = 25,
                epaCityMpg = 20,
                epaHighwayMpg = null
            )
        )

        val trimDetail = TrimDetail.fromTrimDetailResponse(trimDetailResponse)

        assertThat(trimDetail).prop(TrimDetail::fuelEconomy).isEqualTo(null)
    }

    @Test
    fun `currency formatted correctly`() {
        val trimDetailResponse = createTrimDetailResponse(
            msrpDollars = 20000
        )

        val trimDetail = TrimDetail.fromTrimDetailResponse(trimDetailResponse)

        assertThat(trimDetail.msrp).isEqualTo("$20,000")
    }
}