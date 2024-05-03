package com.paulklauser.cars.models

import com.paulklauser.cars.commonapi.CarService
import com.paulklauser.cars.makes.ApiResponse
import javax.inject.Inject

class TrimsRepository @Inject constructor(
    private val carService: CarService
) {

    suspend fun getTrims(makeModelId: String, year: String): ApiResponse<List<Trim>> {
        return ApiResponse.handleApiResponse {
            carService.getTrims(makeModelId, year).data.map { Trim.fromApiTrim(it) }
        }
    }

}