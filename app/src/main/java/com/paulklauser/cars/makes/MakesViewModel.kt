package com.paulklauser.cars.makes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulklauser.cars.MakeAndModelRepository
import com.paulklauser.cars.commonapi.Year
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakesViewModel @Inject constructor(
    private val makeAndModelRepository: MakeAndModelRepository
) : ViewModel() {

    private val vmState = MutableStateFlow(MakesViewModelState(selectedYear = Year.TWENTY_FIFTEEN))
    val uiState = combine(vmState, makeAndModelRepository.state) { vmState, repoState ->
        MakesUiState(
            makes = repoState.makesToModels.keys.toPersistentList(),
            selectedYear = vmState.selectedYear
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        MakesUiState(
            makes = persistentListOf(),
            selectedYear = Year.TWENTY_FIFTEEN
        )
    )

    fun fetchMakes() {
        viewModelScope.launch {
            makeAndModelRepository.fetchCarInfoIfNeeded()
        }
    }

    fun onYearSelected(year: Year) {
        vmState.update { it.copy(selectedYear = year) }
    }
}