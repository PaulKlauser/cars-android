package com.paulklauser.cars.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulklauser.cars.MakeAndModelRepository
import com.paulklauser.cars.makes.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val makeAndModelRepository: MakeAndModelRepository
) : ViewModel() {

    private val makeId: String =
        savedStateHandle[MAKE_ID_PATTERN] ?: throw IllegalArgumentException("Make ID not provided!")
    private val _uiState = MutableStateFlow(ModelsUiState(models = persistentListOf()))
    val uiState = _uiState.asStateFlow()

    fun fetchModels() {
        viewModelScope.launch {
            when (val response = makeAndModelRepository.getModels(makeId)) {
                is ApiResponse.Success -> {
                    _uiState.update { it.copy(models = response.data.toPersistentList()) }
                }

                is ApiResponse.Error -> {
                    // Handle error
                }
            }
        }
    }

}