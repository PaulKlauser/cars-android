package com.paulklauser.cars.models

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ModelsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        ModelsUiState(
            persistentListOf(
                Model("1", "Model 1"),
                Model("2", "Model 2"),
            )
        )
    )
    val uiState = _uiState.asStateFlow()

}