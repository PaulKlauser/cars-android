package com.paulklauser.cars.makes

import com.paulklauser.cars.shared.commonapi.Year
import kotlinx.collections.immutable.ImmutableList

data class MakesUiState(
    val loadingState: LoadingState,
    val selectedYear: Year
) {
    sealed class LoadingState {
        abstract val makes: ImmutableList<Make>?
        data object Loading : LoadingState() {
            override val makes: ImmutableList<Make>? = null
        }

        data class Success(override val makes: ImmutableList<Make>) : LoadingState()

        data object Error : LoadingState() {
            override val makes: ImmutableList<Make>? = null
        }
    }
}
