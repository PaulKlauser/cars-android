package com.paulklauser.cars.makes

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
            )
        )
    )
    var _modelsResponse = ModelsResponse(
        data = listOf(
            ModelsResponse.ApiModel(
                id = 1,
                name = "F-150"
            ),
            ModelsResponse.ApiModel(
                id = 2,
                name = "Mustang"
            ),
            ModelsResponse.ApiModel(
                id = 3,
                name = "Silverado"
            ),
            ModelsResponse.ApiModel(
                id = 4,
                name = "Camaro"
            ),
            ModelsResponse.ApiModel(
                id = 5,
                name = "Corolla"
            ),
            ModelsResponse.ApiModel(
                id = 6,
                name = "Camry"
            )
        )

    )

    override suspend fun getMakes(): MakesResponse {
        return _makesResponse
    }

    override suspend fun getModels(makeId: String, year: String): ModelsResponse {
        return _modelsResponse
    }

}