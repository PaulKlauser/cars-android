package com.paulklauser.cars.makes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulklauser.cars.commonapi.MakeAndModelRepository
import com.paulklauser.cars.commonapi.Year
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
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

    private val vmState = MutableStateFlow(MakesViewModelState(isLoading = true))
    val uiState = combine(
        makeAndModelRepository.state,
        vmState
    ) { repoState, state ->
        MakesUiState(
            loadingState = if (state.isLoading) {
                MakesUiState.LoadingState.Loading
            } else {
                MakesUiState.LoadingState.Success(
                    makes = repoState.makesToModels.keys.toPersistentList()
                )
            },
            selectedYear = repoState.selectedYear
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        MakesUiState(
            loadingState = MakesUiState.LoadingState.Loading,
            selectedYear = Year.TWENTY_FIFTEEN
        )
    )

    private var fetchJob: Job? = null

    fun fetchMakes() {
        fetchJob?.cancel()
        vmState.update { it.copy(isLoading = true) }
        fetchJob = viewModelScope.launch {
            makeAndModelRepository.fetchCarInfoIfNeeded()
            vmState.update { it.copy(isLoading = false) }
        }
    }

    fun onYearSelected(year: Year) {
        fetchJob?.cancel()
        vmState.update { it.copy(isLoading = true) }
        fetchJob = viewModelScope.launch {
            makeAndModelRepository.selectYear(year)
            vmState.update { it.copy(isLoading = false) }
        }
    }
}