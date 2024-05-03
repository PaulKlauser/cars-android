package com.paulklauser.cars.models

import com.paulklauser.cars.commonapi.MakeAndModelRepository
import com.paulklauser.cars.makes.ApiResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class FetchTrimsUseCase @Inject constructor(
    private val trimsRepository: TrimsRepository,
    private val makeAndModelRepository: MakeAndModelRepository
) {
    suspend operator fun invoke(models: List<Model>): ApiResponse<Map<Model, List<Trim>>> {
        return coroutineScope {
            val trimRequests = models.associateWith {
                async {
                    trimsRepository.getTrims(
                        makeModelId = it.id,
                        year = makeAndModelRepository.state.value.selectedYear.value
                    )
                }
            }
            val modelToTrimResponses = trimRequests.mapValues {
                it.value.await()
            }
            if (modelToTrimResponses.values.any { it is ApiResponse.Error }) {
                ApiResponse.Error
            } else {
                ApiResponse.Success(
                    modelToTrimResponses.mapValues {
                        (it.value as ApiResponse.Success).data
                    }
                )
            }
        }
    }
}