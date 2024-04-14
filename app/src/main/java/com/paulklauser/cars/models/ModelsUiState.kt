package com.paulklauser.cars.models

import kotlinx.collections.immutable.ImmutableList

data class ModelsUiState(
    val loadingState: LoadingState
) {
    sealed class LoadingState {
        data object Loading : LoadingState()
        data class Success(
            val models: ImmutableList<ModelRowItem>,
            val make: String
        ) : LoadingState()
    }
}
