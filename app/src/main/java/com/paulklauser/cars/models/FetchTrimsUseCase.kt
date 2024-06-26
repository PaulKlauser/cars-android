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
            // This technically won't actually make all trim requests in parallel, but the complexity
            // of doing so and associating the trims back to the models isn't worth it right now.
            val modelToTrimResponses = trimRequests.mapValues {
                it.value.await()
            }
            if (modelToTrimResponses.values.any { it is ApiResponse.Error }) {
                ApiResponse.Error
            } else {
                ApiResponse.Success(
                    modelToTrimResponses.mapValues {
                        (it.value as ApiResponse.Success).data
                    }.filterValues {
                        it.isNotEmpty()
                    }
                )
            }
        }
    }
}