package com.paulklauser.cars.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulklauser.cars.commonapi.MakeAndModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val makeAndModelRepository: MakeAndModelRepository,
    private val trimsRepository: TrimsRepository
) : ViewModel() {

    private val makeId: String =
        savedStateHandle[MAKE_ID_PATTERN] ?: throw IllegalArgumentException("Make ID not provided!")
    val uiState = makeAndModelRepository.state.map { makeAndModelState ->
        val make = requireNotNull(makeAndModelState.makesToModels.keys.find { it.id == makeId }) {
            "Make ID not found in map of makes to models!"
        }
        val models = requireNotNull(makeAndModelState.makesToModels[make]) {
            "Make ID not found in map of makes to models!"
        }
        val modelToTrims = coroutineScope {
            val trimRequests = models.associateWith {
                async {
                    // TODO: PK - I don't love grabbing the year from the other repo like this.
                    //  Smells like a use case might be warranted.
                    trimsRepository.getTrims(
                        makeModelId = it.id,
                        year = makeAndModelRepository.state.value.selectedYear.value
                    )
                }
            }
            trimRequests.mapValues { it.value.await() }
        }
        val modelItems =
            models.map {
                ModelRowItem(
                    model = it,
                    trims = modelToTrims[it]!!
                )
            }.toPersistentList()
        ModelsUiState(
            models = modelItems,
            make = make.name
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        ModelsUiState(
            models = persistentListOf(),
            make = ""
        )
    )

    fun fetchIfNeeded() {
        viewModelScope.launch {
            makeAndModelRepository.fetchCarInfoIfNeeded()

        }
    }

}