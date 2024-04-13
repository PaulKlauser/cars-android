package com.paulklauser.cars.models

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.paulklauser.cars.MainDispatcherRule
import com.paulklauser.cars.MakeAndModelRepository
import com.paulklauser.cars.makes.FakeCarService
import com.paulklauser.cars.makes.MakesResponse
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ModelsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val carService = FakeCarService()

    private fun createViewModel(): ModelsViewModel {
        return ModelsViewModel(
            savedStateHandle = SavedStateHandle(mapOf(MAKE_ID_PATTERN to "1")),
            makeAndModelRepository = MakeAndModelRepository(carService)
        )
    }

    @Test
    fun `uiState contains models for make ID`() = runTest {
        carService._makesResponse = MakesResponse(
            data = listOf(
                MakesResponse.ApiMake(
                    id = 1,
                    name = "Ford"
                ),
            )
        )
        carService._modelMap = mapOf(
            "1" to ModelsResponse(
                listOf(
                    ModelsResponse.ApiModel(
                        id = 1,
                        name = "F-150",
                        makeId = 1
                    ),
                    ModelsResponse.ApiModel(
                        id = 2,
                        name = "Mustang",
                        makeId = 1
                    )
                )
            )
        )
        val viewModel = createViewModel()
        viewModel.fetchIfNeeded()

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                ModelsUiState(
                    models = persistentListOf(
                        Model(id = "1", name = "F-150", makeId = "1"),
                        Model(id = "2", name = "Mustang", makeId = "1")
                    ),
                    make = "Ford"
                )
            )
        }
    }

}