package com.paulklauser.cars.modeldetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ModelDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        ModelDetailUiState(
            modelDetail = ModelDetail(
                year = "2022",
                description = "This is a car",
                msrp = "$100,000"
            )
        )
    )
    val uiState = _uiState.asStateFlow()

}