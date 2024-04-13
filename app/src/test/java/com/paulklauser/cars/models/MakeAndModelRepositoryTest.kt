package com.paulklauser.cars.models

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.paulklauser.cars.MakeAndModelRepository
import com.paulklauser.cars.MakeAndModelRepositoryState
import com.paulklauser.cars.makes.FakeCarService
import com.paulklauser.cars.makes.Make
import com.paulklauser.cars.makes.MakesResponse
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MakeAndModelRepositoryTest {

    private val carService = FakeCarService()

    private fun createRepository(): MakeAndModelRepository {
        return MakeAndModelRepository(carService)
    }

    @Test
    fun `returns mapping of makes to models successfully`() = runTest {
        carService._makesResponse = MakesResponse(
            data = listOf(
                MakesResponse.ApiMake(
                    id = 1,
                    name = "BMW"
                ),
                MakesResponse.ApiMake(
                    id = 2,
                    name = "Volvo"
                )
            )
        )
        carService._modelMap = mapOf(
            "1" to ModelsResponse(
                listOf(
                    ModelsResponse.ApiModel(
                        id = 1,
                        name = "M3",
                        makeId = 1
                    )
                )
            ),
            "2" to ModelsResponse(
                listOf(
                    ModelsResponse.ApiModel(
                        id = 4,
                        name = "XC60",
                        makeId = 2
                    )
                )
            )
        )

        val repository = createRepository()
        repository.fetchCarInfoIfNeeded()

        repository.state.test {
            assertThat(awaitItem()).isEqualTo(
                MakeAndModelRepositoryState(
                    makesToModels = mapOf(
                        Make(id = "1", name = "BMW") to listOf(
                            Model(id = "1", name = "M3", makeId = "1")
                        ),
                        Make(id = "2", name = "Volvo") to listOf(
                            Model(id = "4", name = "XC60", makeId = "2")
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `filters out makes with no models`() = runTest {
        carService._makesResponse = MakesResponse(
            data = listOf(
                MakesResponse.ApiMake(
                    id = 1,
                    name = "BMW"
                ),
                MakesResponse.ApiMake(
                    id = 2,
                    name = "Volvo"
                )
            )
        )
        carService._modelMap = mapOf(
            "1" to ModelsResponse(
                listOf(
                    ModelsResponse.ApiModel(
                        id = 1,
                        name = "M3",
                        makeId = 1
                    )
                )
            )
        )

        val repository = createRepository()
        repository.fetchCarInfoIfNeeded()

        repository.state.test {
            assertThat(awaitItem()).isEqualTo(
                MakeAndModelRepositoryState(
                    makesToModels = mapOf(
                        Make(id = "1", name = "BMW") to listOf(
                            Model(id = "1", name = "M3", makeId = "1")
                        )
                    )
                )
            )
        }
    }

}