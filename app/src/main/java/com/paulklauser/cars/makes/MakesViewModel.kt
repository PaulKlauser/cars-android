package com.paulklauser.cars.makes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulklauser.cars.commonapi.MakeAndModelRepository
import com.paulklauser.cars.commonapi.Year
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakesViewModel @Inject constructor(
    private val makeAndModelRepository: MakeAndModelRepository
) : ViewModel() {

    val uiState = makeAndModelRepository.state.map { repoState ->
        MakesUiState(
            makes = repoState.makesToModels.keys.toPersistentList(),
            selectedYear = repoState.selectedYear
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        MakesUiState(
            makes = persistentListOf(),
            selectedYear = Year.TWENTY_FIFTEEN
        )
    )

    private var fetchJob: Job? = null

    fun fetchMakes() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            makeAndModelRepository.fetchCarInfoIfNeeded()
        }
    }

    fun onYearSelected(year: Year) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            makeAndModelRepository.selectYear(year)
        }
    }
}