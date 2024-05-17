package com.paulklauser.cars.commonapi

import com.paulklauser.cars.makes.Make
import com.paulklauser.cars.models.Model
import com.paulklauser.cars.shared.commonapi.Year

data class MakeAndModelRepositoryState(
    val loadingState: LoadingState,
    val selectedYear: Year
) {
    sealed class LoadingState {
        data object Loading : LoadingState()
        data class Success(
            val makesToModels: Map<Make, List<Model>>,
        ) : LoadingState()

        data object Error : LoadingState()
    }
}