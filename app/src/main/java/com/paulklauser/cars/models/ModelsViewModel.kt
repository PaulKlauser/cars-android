package com.paulklauser.cars.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulklauser.cars.commonapi.MakeAndModelRepository
import com.paulklauser.cars.commonapi.MakeAndModelRepositoryState
import com.paulklauser.cars.makes.ApiResponse
import com.paulklauser.cars.makes.Make
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val makeAndModelRepository: MakeAndModelRepository,
    private val fetchTrimsUseCase: FetchTrimsUseCase
) : ViewModel() {

    private val makeId: String =
        savedStateHandle[MAKE_ID_PATTERN] ?: throw IllegalArgumentException("Make ID not provided!")
    val uiState = makeAndModelRepository.state.map { makeAndModelState ->
        when (makeAndModelState.loadingState) {
            MakeAndModelRepositoryState.LoadingState.Error -> ModelsUiState(
                loadingState = ModelsUiState.LoadingState.Error,
                make = ""
            )

            MakeAndModelRepositoryState.LoadingState.Loading -> ModelsUiState(
                loadingState = ModelsUiState.LoadingState.Loading,
                make = ""
            )

            is MakeAndModelRepositoryState.LoadingState.Success -> resolveSuccessState(
                makeAndModelState.loadingState.makesToModels
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        ModelsUiState(loadingState = ModelsUiState.LoadingState.Loading, make = "")
    )

    private suspend fun resolveSuccessState(makesToModels: Map<Make, List<Model>>): ModelsUiState {
        val make = requireNotNull(makesToModels.keys.find { it.id == makeId }) {
            "Make ID not found in map of makes to models!"
        }
        val models = requireNotNull(makesToModels[make]) {
            "Make ID not found in map of makes to models!"
        }
        val modelToTrims = when (val modelToTrimsResponse = fetchTrimsUseCase(models)) {
            ApiResponse.Error -> return ModelsUiState(
                loadingState = ModelsUiState.LoadingState.Error,
                make = ""
            )

            is ApiResponse.Success -> modelToTrimsResponse.data
        }
        val modelItems = models.map {
            ModelRowItem(
                model = it,
                trims = modelToTrims[it]!!
            )
        }.toPersistentList()
        return ModelsUiState(
            loadingState = ModelsUiState.LoadingState.Success(
                models = modelItems
            ),
            make = make.name
        )
    }

    fun fetchIfNeeded() {
        viewModelScope.launch {
            makeAndModelRepository.fetchCarInfoIfNeeded()
        }
    }

}