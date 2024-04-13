package com.paulklauser.cars

import com.paulklauser.cars.makes.ApiResponse
import com.paulklauser.cars.makes.CarService
import com.paulklauser.cars.makes.Make
import com.paulklauser.cars.models.Model
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MakeAndModelRepository @Inject constructor(
    private val carService: CarService
) {

    private val _state = MutableStateFlow(
        MakeAndModelRepositoryState(makesToModels = emptyMap())
    )
    val state = _state.asStateFlow()

    suspend fun fetchCarInfo() {
        val makes = carService.getMakes().data.map { Make.fromApi(it) }
        val makesToModels = coroutineScope {
            val foo = makes.map {
                async {
                    carService.getModels(
                        makeId = it.id,
                        year = "2015"
                    ).data.map { Model.fromApiModel(it) }
                }
            }
            foo.awaitAll()
                .filter { it.isNotEmpty() }
                .associateBy { models ->
                    makes.first {
                        // This is assuming that all models in the list belong to the same make
                        models.first().makeId == it.id
                    }
                }
        }
        _state.update { it.copy(makesToModels = makesToModels) }

    }

    suspend fun getModels(makeId: String): ApiResponse<List<Model>> {
        return ApiResponse.handleApiResponse {
            // TODO: Allow user to choose year
            carService.getModels(makeId, "2015").data.map { Model.fromApiModel(it) }
        }
    }
}
