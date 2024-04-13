package com.paulklauser.cars

import com.paulklauser.cars.commonapi.Year
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
        MakeAndModelRepositoryState(
            makesToModels = emptyMap(),
            selectedYear = Year.TWENTY_FIFTEEN
        )
    )
    val state = _state.asStateFlow()

    suspend fun selectYear(year: Year) {
        _state.update { it.copy(selectedYear = year) }
        fetchCarInfo()
    }

    suspend fun fetchCarInfoIfNeeded() {
        if (state.value.makesToModels.isNotEmpty()) {
            return
        }
        fetchCarInfo()
    }

    private suspend fun fetchCarInfo() {
        val makes = carService.getMakes().data.map { Make.fromApi(it) }
        val makesToModels = coroutineScope {
            val foo = makes.map {
                async {
                    carService.getModels(
                        makeId = it.id,
                        year = state.value.selectedYear.value
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
}
