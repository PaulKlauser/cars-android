package com.paulklauser.cars.makes

import com.paulklauser.cars.commonapi.CarService
import com.paulklauser.cars.modeldetail.TrimResponse
import com.paulklauser.cars.models.ModelsResponse

class FakeCarService : CarService {

    var _makesResponse = MakesResponse(
        data = listOf(
            MakesResponse.ApiMake(
                id = 1,
                name = "Ford"
            ),
            MakesResponse.ApiMake(
                id = 2,
                name = "Chevrolet"
            ),
            MakesResponse.ApiMake(
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

    override suspend fun getMakes(): MakesResponse {
        return _makesResponse
    }

    override suspend fun getModels(makeId: String, year: String): ModelsResponse {
        return _modelMap[makeId] ?: ModelsResponse(emptyList())
    }

    override suspend fun getTrim(trimId: String): TrimResponse {
        TODO("Not yet implemented")
    }

}