package com.paulklauser.cars.trimdetail

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
class TrimDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val trimDetailRepository: TrimDetailRepository
) : ViewModel() {

    private val trimId: String =
        savedStateHandle[TRIM_ID_PATTERN]
            ?: throw IllegalArgumentException("Trim ID not provided!")

    private val _uiState =
        MutableStateFlow(TrimDetailUiState(loadingState = TrimDetailUiState.LoadingState.Loading))
    val uiState = _uiState.asStateFlow()

    fun fetchTrimDetail() {
        viewModelScope.launch {
            when (val response = trimDetailRepository.getTrimDetails(trimId)) {
                is ApiResponse.Success -> {
                    _uiState.update {
                        it.copy(
                            loadingState = TrimDetailUiState.LoadingState.Success(
                                response.data
                            )
                        )
                    }
                }

                is ApiResponse.Error -> {
                    // TODO: PK
                }
            }
        }
    }

}