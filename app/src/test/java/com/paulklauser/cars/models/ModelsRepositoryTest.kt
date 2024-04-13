package com.paulklauser.cars.models

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import com.paulklauser.cars.makes.ApiResponse
import com.paulklauser.cars.makes.FakeCarService
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ModelsRepositoryTest {

    private val carService = FakeCarService()

    private fun createRepository(): ModelsRepository {
        return ModelsRepository(carService)
    }

    @Test
    fun `return list of models successfully`() = runTest {
        carService._modelsResponse = ModelsResponse(
            data = listOf(
                ModelsResponse.ApiModel(
                    id = 1,
                    name = "F-150"
                ),
                ModelsResponse.ApiModel(
                    id = 2,
                    name = "Mustang"
                ),
                ModelsResponse.ApiModel(
                    id = 3,
                    name = "Focus"
                )
            )
        )
        val repository = createRepository()

        val models = repository.getModels("1")

        assertThat(models).isInstanceOf<ApiResponse.Success<List<Model>>>()
            .prop(ApiResponse.Success<List<Model>>::data)
            .containsExactly(
                Model(
                    id = "1",
                    name = "F-150"
                ),
                Model(
                    id = "2",
                    name = "Mustang"
                ),
                Model(
                    id = "3",
                    name = "Focus"
                )
            )
    }

}