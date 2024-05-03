package com.paulklauser.cars.commonapi

import com.paulklauser.cars.makes.Make
import com.paulklauser.cars.models.Model
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MakeAndModelRepository @Inject constructor(
    private val carService: CarService
) {

    private val _state = MutableStateFlow(
        MakeAndModelRepositoryState(
            loadingState = MakeAndModelRepositoryState.LoadingState.Loading,
            selectedYear = Year.TWENTY_FIFTEEN
        )
    )
    val state = _state.asStateFlow()

    suspend fun selectYear(year: Year) {
        _state.update { it.copy(selectedYear = year) }
        fetchCarInfo()
    }

    suspend fun fetchCarInfoIfNeeded() {
        if (state.value.loadingState is MakeAndModelRepositoryState.LoadingState.Success) {
            return
        }

        _state.update {
            it.copy(loadingState = MakeAndModelRepositoryState.LoadingState.Loading)
        }
        try {
            _state.update {
                it.copy(
                    loadingState = MakeAndModelRepositoryState.LoadingState.Success(
                        makesToModels = fetchCarInfo()
                    )
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
            _state.update {
                it.copy(loadingState = MakeAndModelRepositoryState.LoadingState.Error)
            }
        }
    }

    private suspend fun fetchCarInfo(): Map<Make, List<Model>> {
        val makes = carService.getMakes().data.map { Make.fromApi(it) }
        return coroutineScope {
            makes.map {
                async {
                    carService.getModels(
                        makeId = it.id,
                        year = state.value.selectedYear.value
                    ).data.map { Model.fromApiModel(it) }
                }
            }
                .awaitAll()
                .filter { it.isNotEmpty() }
                .associateBy { models ->
                    makes.first {
                        // This is assuming that all models in the list belong to the same make
                        models.first().makeId == it.id
                    }
                }
        }
    }
}
