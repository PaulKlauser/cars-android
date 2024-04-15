package com.paulklauser.cars.trimdetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.paulklauser.cars.MainDispatcherRule
import com.paulklauser.cars.commonapi.FakeCarService
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class TrimDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val carService = FakeCarService()

    private fun createViewModel(): TrimDetailViewModel {
        return TrimDetailViewModel(
            SavedStateHandle(mapOf(TRIM_ID_PATTERN to "123")),
            TrimDetailRepository(carService)
        )
    }

    @Test
    fun `failure to fetch trim detail gives error state`() = runTest {
        carService._shouldErrorOnTrimDetails = true
        val viewModel = createViewModel()

        viewModel.fetchTrimDetail()

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(
                TrimDetailUiState(
                    loadingState = TrimDetailUiState.LoadingState.Error
                )
            )
        }
    }
}