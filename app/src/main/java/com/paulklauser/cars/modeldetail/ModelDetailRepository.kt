package com.paulklauser.cars.modeldetail

import com.paulklauser.cars.commonapi.CarService
import com.paulklauser.cars.makes.ApiResponse
import javax.inject.Inject

class ModelDetailRepository @Inject constructor(
    private val carService: CarService
) {
    suspend fun getTrimDetails(modelId: String): ApiResponse<ModelDetail> {
        return ApiResponse.handleApiResponse {
            ModelDetail.fromTrimResponse(
                carService.getTrim(
                    modelId
                )
            )
        }
    }
}