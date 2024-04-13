package com.paulklauser.cars.models

import com.paulklauser.cars.makes.ApiResponse
import com.paulklauser.cars.makes.CarService
import javax.inject.Inject

class ModelsRepository @Inject constructor(
    private val carService: CarService
) {
    suspend fun getModels(makeId: String): ApiResponse<List<Model>> {
        return ApiResponse.handleApiResponse {
            // TODO: Allow user to choose year
            carService.getModels(makeId, "2015").data.map { Model.fromApiModel(it) }
        }
    }
}