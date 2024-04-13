package com.paulklauser.cars.makes

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MakesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        MakesUiState(
            makes = persistentListOf(
                Make("Toyota"),
                Make("Ford"),
                Make("Chevrolet")
            )
        )
    )
    val uiState = _uiState.asStateFlow()
}