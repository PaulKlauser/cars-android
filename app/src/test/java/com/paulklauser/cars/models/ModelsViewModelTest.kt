package com.paulklauser.cars.models

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.paulklauser.cars.MainDispatcherRule
import com.paulklauser.cars.commonapi.FakeCarService
import com.paulklauser.cars.commonapi.MakeAndModelRepository
import com.paulklauser.cars.makes.ApiMake
import com.paulklauser.cars.makes.MakesResponse
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ModelsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val carService = FakeCarService()
    private val makeAndModelRepository = MakeAndModelRepository(carService)

    private fun createViewModel(): ModelsViewModel {
        return ModelsViewModel(
            savedStateHandle = SavedStateHandle(mapOf(MAKE_ID_PATTERN to "1")),
            makeAndModelRepository = makeAndModelRepository,
            fetchTrimsUseCase = FetchTrimsUseCase(
                trimsRepository = TrimsRepository(carService),
                makeAndModelRepository = makeAndModelRepository
            )
        )
    }

    @Test
    fun `uiState contains models and trims for make ID`() = runTest {
        carService._makesResponse = MakesResponse(
            data = listOf(
                ApiMake(
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
        carService._trimsMap = mapOf(
            "1" to ModelTrimResponse(
                listOf(
                    ModelTrimResponse.ModelTrim(
                        id = 1,
                        description = "XL"
                    ),
                    ModelTrimResponse.ModelTrim(
                        id = 2,
                        description = "XLT"
                    )
                )
            ),
            "2" to ModelTrimResponse(
                listOf(
                    ModelTrimResponse.ModelTrim(
                        id = 3,
                        description = "EcoBoost"
                    ),
                    ModelTrimResponse.ModelTrim(
                        id = 4,
                        description = "GT"
                    )
                )
            )
        )
        val viewModel = createViewModel()
        viewModel.fetchIfNeeded()

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                ModelsUiState(
                    loadingState = ModelsUiState.LoadingState.Success(
                        models = persistentListOf(
                            ModelRowItem(
                                model = Model(
                                    id = "1",
                                    name = "F-150",
                                    makeId = "1"
                                ),
                                trims = listOf(
                                    Trim(
                                        id = "1",
                                        description = "XL"
                                    ),
                                    Trim(
                                        id = "2",
                                        description = "XLT"
                                    )
                                )
                            ),
                            ModelRowItem(
                                model = Model(
                                    id = "2",
                                    name = "Mustang",
                                    makeId = "1"
                                ),
                                trims = listOf(
                                    Trim(
                                        id = "3",
                                        description = "EcoBoost"
                                    ),
                                    Trim(
                                        id = "4",
                                        description = "GT"
                                    )
                                )
                            )
                        )
                    ),
                    make = "Ford"
                )
            )
        }
    }

    @Test
    fun `models with no trims returned are filtered out`() = runTest {
        carService._makesResponse = MakesResponse(
            data = listOf(
                ApiMake(
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
        carService._trimsMap = mapOf(
            "1" to ModelTrimResponse(
                listOf(
                    ModelTrimResponse.ModelTrim(
                        id = 1,
                        description = "XL"
                    ),
                    ModelTrimResponse.ModelTrim(
                        id = 2,
                        description = "XLT"
                    )
                )
            )
        )
        val viewModel = createViewModel()
        viewModel.fetchIfNeeded()

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                ModelsUiState(
                    loadingState = ModelsUiState.LoadingState.Success(
                        models = persistentListOf(
                            ModelRowItem(
                                model = Model(
                                    id = "1",
                                    name = "F-150",
                                    makeId = "1"
                                ),
                                trims = listOf(
                                    Trim(
                                        id = "1",
                                        description = "XL"
                                    ),
                                    Trim(
                                        id = "2",
                                        description = "XLT"
                                    )
                                )
                            )
                        )
                    ),
                    make = "Ford"
                )
            )
        }
    }

    @Test
    fun `failure to fetch models gives error state`() = runTest {
        carService._shouldErrorOnModels = true
        val viewModel = createViewModel()

        viewModel.fetchIfNeeded()

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                ModelsUiState(
                    loadingState = ModelsUiState.LoadingState.Error,
                    make = ""
                )
            )
        }
    }

    @Test
    fun `failure to fetch trims gives error state`() = runTest {
        carService._shouldErrorOnTrims = true
        val viewModel = createViewModel()

        viewModel.fetchIfNeeded()

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                ModelsUiState(
                    loadingState = ModelsUiState.LoadingState.Error,
                    make = ""
                )
            )
        }
    }

}