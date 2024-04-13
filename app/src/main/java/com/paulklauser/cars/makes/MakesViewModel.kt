package com.paulklauser.cars.makes

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
class MakesViewModel @Inject constructor(
    private val makeAndModelRepository: MakeAndModelRepository
) : ViewModel() {
    val uiState = makeAndModelRepository.state.map {
        MakesUiState(makes = it.makesToModels.keys.toPersistentList())
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        MakesUiState(persistentListOf())
    )

    fun fetchMakes() {
        viewModelScope.launch {
            makeAndModelRepository.fetchCarInfo()
        }
    }
}