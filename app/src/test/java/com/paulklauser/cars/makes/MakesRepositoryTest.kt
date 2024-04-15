package com.paulklauser.cars.makes

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import com.paulklauser.cars.commonapi.FakeCarService
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MakesRepositoryTest {

    private val makesService = FakeCarService()

    private fun createRepository(): MakesRepository {
        return MakesRepository(makesService)
    }

    @Test
    fun `return list of makes successfully`() = runTest {
        makesService._makesResponse = MakesResponse(
            data = listOf(
                ApiMake(
                    id = 1,
                    name = "Ford"
                ),
                ApiMake(
                    id = 2,
                    name = "Chevrolet"
                ),
                ApiMake(
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