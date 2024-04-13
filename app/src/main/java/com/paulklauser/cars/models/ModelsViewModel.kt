package com.paulklauser.cars.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulklauser.cars.MakeAndModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val makeAndModelRepository: MakeAndModelRepository
) : ViewModel() {

    private val makeId: String =
        savedStateHandle[MAKE_ID_PATTERN] ?: throw IllegalArgumentException("Make ID not provided!")
    val uiState = makeAndModelRepository.state.map { makeAndModelState ->
        val models = requireNotNull(
            makeAndModelState.makesToModels.keys.find { it.id == makeId }?.let {
                makeAndModelState.makesToModels[it]
            }
        ) { "Make ID not found in map of makes to models!" }
        ModelsUiState(models = models.toPersistentList())
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        ModelsUiState(persistentListOf())
    )

    fun fetchIfNeeded() {
        viewModelScope.launch {
            makeAndModelRepository.fetchCarInfoIfNeeded()
        }
    }

}