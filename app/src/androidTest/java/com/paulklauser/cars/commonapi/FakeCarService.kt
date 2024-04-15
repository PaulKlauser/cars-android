package com.paulklauser.cars.commonapi

import com.paulklauser.cars.makes.ApiMake
import com.paulklauser.cars.makes.MakesResponse
import com.paulklauser.cars.models.ModelTrimResponse
import com.paulklauser.cars.models.ModelsResponse
import com.paulklauser.cars.trimdetail.TrimDetailResponse

/**
 * Duplicated between test and androidTest in lieu of setting up a shared basetest module right now.
 */
@Suppress("TooGenericExceptionThrown")
class FakeCarService : CarService {

    var _makesResponse = MakesResponse(
        data = listOf(
            ApiMake(
                id = 1,
                name = "Ford"
            ),
            ApiMake(
                id = 2,
                name = "Chevrolet"
            ),
            ApiMake(
                id = 3,
                name = "Toyota"
            ),
        )
    )
    var _modelMap = mapOf(
        "1" to ModelsResponse(
            listOf(
                ModelsResponse.ApiModel(
                    id = 1,
                    name = "F-150",
                    makeId = 1
                ),
                ModelsResponse.ApiModel(
                    id = 2,
                    name = "Mustang",
                    makeId = 1
                )
            )
        ),
        "2" to ModelsResponse(
            listOf(
                ModelsResponse.ApiModel(
                    id = 3,
                    name = "Silverado",
                    makeId = 2
                ),
                ModelsResponse.ApiModel(
                    id = 4,
                    name = "Camaro",
                    makeId = 2
                )
            )
        )
    )
    var _trimsMap = mapOf(
        "1" to ModelTrimResponse(
            listOf(
                ModelTrimResponse.ModelTrim(
                    id = 1,
                    description = "LE"
                ),
            )
        ),
        "2" to ModelTrimResponse(
            listOf(
                ModelTrimResponse.ModelTrim(
                    id = 2,
                    description = "SE"
                ),
            )
        )
    )
    var _shouldErrorOnModels = false
    var _shouldErrorOnTrims = false
    var _shouldErrorOnTrimDetails = false

    override suspend fun getMakes(): MakesResponse {
        return _makesResponse
    }

    override suspend fun getModels(makeId: String, year: String): ModelsResponse {
        if (_shouldErrorOnModels) {
            throw Exception()
        }
        return _modelMap[makeId] ?: ModelsResponse(emptyList())
    }

    override suspend fun getTrimDetails(trimId: String): TrimDetailResponse {
        if (_shouldErrorOnTrimDetails) {
            throw Exception()
        }
        return TrimDetailResponse(
            year = 2021,
            name = "Corolla",
            description = "LE",
            msrpDollars = 20000,
            makeModel = TrimDetailResponse.MakeModel(
                name = "Corolla",
                make = ApiMake(
                    id = 1,
                    name = "Toyota"
                )
            ),
            makeModelTrimMileage = TrimDetailResponse.MakeModelTrimMileage(
                combinedMpg = 30,
                epaCityMpg = 25,
                epaHighwayMpg = 35
            ),
            makeModelTrimEngine = TrimDetailResponse.MakeModelTrimEngine(
                horsepowerHp = 150,
                torqueFtLbs = 150
            )
        )
    }

    override suspend fun getTrims(makeModelId: String, year: String): ModelTrimResponse {
        if (_shouldErrorOnTrims) {
            throw Exception()
        }
        return _trimsMap[makeModelId] ?: ModelTrimResponse(emptyList())
    }

}