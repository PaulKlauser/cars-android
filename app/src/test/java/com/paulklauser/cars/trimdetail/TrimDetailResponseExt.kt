package com.paulklauser.cars.trimdetail

import com.paulklauser.cars.makes.ApiMake

fun createTrimDetailResponse(
    year: Int = 2021,
    name: String = "2021 Acura RDX",
    description: String = "The 2021 Acura RDX is a compact luxury SUV that seats five people.",
    msrpDollars: Int = 38200,
    makeModel: TrimDetailResponse.MakeModel = TrimDetailResponse.MakeModel(
        name = "RDX",
        make = ApiMake(
            id = 1,
            name = "Acura"
        )
    ),
    makeModelTrimMileage: TrimDetailResponse.MakeModelTrimMileage = TrimDetailResponse.MakeModelTrimMileage(
        combinedMpg = 24,
        epaCityMpg = 22,
        epaHighwayMpg = 28
    ),
    makeModelTrimEngine: TrimDetailResponse.MakeModelTrimEngine = TrimDetailResponse.MakeModelTrimEngine(
        horsepowerHp = 272,
        torqueFtLbs = 280
    )
): TrimDetailResponse = TrimDetailResponse(
    year = year,
    name = name,
    description = description,
    msrpDollars = msrpDollars,
    makeModel = makeModel,
    makeModelTrimMileage = makeModelTrimMileage,
    makeModelTrimEngine = makeModelTrimEngine
)