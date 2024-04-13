package com.paulklauser.cars.makes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakesViewModel @Inject constructor(
    private val makesRepository: MakesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MakesUiState(makes = persistentListOf())
    )
    val uiState = _uiState.asStateFlow()

    fun fetchMakes() {
        viewModelScope.launch {
            when (val response = makesRepository.getMakes()) {
                is ApiResponse.Success -> {
                    _uiState.update { it.copy(makes = response.data.toPersistentList()) }
                }

                is ApiResponse.Error -> {
                    // TODO: PK
                }
            }
        }
    }
}