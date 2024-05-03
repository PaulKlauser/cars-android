package com.paulklauser.cars.makes

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.paulklauser.cars.MainDispatcherRule
import com.paulklauser.cars.commonapi.FakeCarService
import com.paulklauser.cars.commonapi.MakeAndModelRepository
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class MakesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val carService = FakeCarService()
    private val makeAndModelRepository = MakeAndModelRepository(carService)

    private fun createViewModel(): MakesViewModel {
        return MakesViewModel(makeAndModelRepository)
    }

    @Test
    fun `fetch makes called repeatedly before completion does not duplicate network call`() = runTest {
        carService._makesDelay = 1000.milliseconds
        val viewModel = createViewModel()

        viewModel.fetchMakes()
        advanceTimeBy(500.milliseconds)
        viewModel.fetchMakes()

        assertThat(carService._makesCallCount).isEqualTo(1)
    }

}