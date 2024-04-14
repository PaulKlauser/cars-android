package com.paulklauser.cars.modeldetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulklauser.cars.makes.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val modelDetailRepository: ModelDetailRepository
) : ViewModel() {

    private val modelId: String =
        savedStateHandle[MODEL_ID_PATTERN]
            ?: throw IllegalArgumentException("Make ID not provided!")

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

    fun fetchModelDetail() {
        viewModelScope.launch {
            when (val response = modelDetailRepository.getTrimDetails(modelId)) {
                is ApiResponse.Success -> {
                    _uiState.update { it.copy(modelDetail = response.data) }
                }

                is ApiResponse.Error -> {
                    // TODO: PK
                }
            }
        }
    }

}