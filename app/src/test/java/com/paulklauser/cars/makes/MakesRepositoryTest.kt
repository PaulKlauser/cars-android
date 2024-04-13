package com.paulklauser.cars.makes

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MakesRepositoryTest {

    private val makesService = FakeMakesService()

    private fun createRepository(): MakesRepository {
        return MakesRepository(makesService)
    }

    @Test
    fun `return list of makes successfully`() = runTest {
        makesService._makesResponse = MakesResponse(
            data = listOf(
                MakesResponse.ApiMake(
                    id = 1,
                    name = "Ford"
                ),
                MakesResponse.ApiMake(
                    id = 2,
                    name = "Chevrolet"
                ),
                MakesResponse.ApiMake(
                    id = 3,
                    name = "Toyota"
                )
            )
        )
        val repository = createRepository()

        val makes = repository.getMakes()

        assertThat(makes).isInstanceOf<ApiResponse.Success<List<Make>>>()
            .prop(ApiResponse.Success<List<Make>>::data)
            .containsExactly(
                Make(
                    id = "1",
                    name = "Ford"
                ),
                Make(
                    id = "2",
                    name = "Chevrolet"
                ),
                Make(
                    id = "3",
                    name = "Toyota"
                )
            )
    }

}