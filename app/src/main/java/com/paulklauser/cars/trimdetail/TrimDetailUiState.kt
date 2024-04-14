package com.paulklauser.cars.trimdetail

data class TrimDetailUiState(
    val loadingState: LoadingState
) {
    sealed class LoadingState {
        abstract val trimDetail: TrimDetail?

        data object Loading : LoadingState() {
            override val trimDetail: TrimDetail? = null
        }

        data class Success(override val trimDetail: TrimDetail) : LoadingState()
    }
}