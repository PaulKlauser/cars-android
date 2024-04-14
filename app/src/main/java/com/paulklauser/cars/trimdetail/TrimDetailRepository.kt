package com.paulklauser.cars.trimdetail

import com.paulklauser.cars.commonapi.CarService
import com.paulklauser.cars.makes.ApiResponse
import javax.inject.Inject

class TrimDetailRepository @Inject constructor(
    private val carService: CarService
) {
    suspend fun getTrimDetails(modelId: String): ApiResponse<TrimDetail> {
        return ApiResponse.handleApiResponse {
            TrimDetail.fromTrimDetailResponse(
                carService.getTrimDetails(
                    modelId
                )
            )
        }
    }
}