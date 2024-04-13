package com.paulklauser.cars.makes

class FakeMakesService : MakesService {

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

    override suspend fun getMakes(): MakesResponse {
        return _makesResponse
    }

}