package com.paulklauser.cars.makes

import javax.inject.Inject

class MakesRepository @Inject constructor(
    private val carService: CarService
) {

    suspend fun getMakes(): ApiResponse<List<Make>> {
        return ApiResponse.handleApiResponse {
            carService.getMakes().data.map { Make.fromApi(it) }
        }
    }

}