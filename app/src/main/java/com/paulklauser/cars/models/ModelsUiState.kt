package com.paulklauser.cars.models

import kotlinx.collections.immutable.ImmutableList

data class ModelsUiState(
    val loadingState: LoadingState,
    val make: String
) {
    sealed class LoadingState {
        data object Loading : LoadingState()
        data class Success(
            val models: ImmutableList<ModelRowItem>,
        ) : LoadingState()
        data object Error : LoadingState()
    }
}
