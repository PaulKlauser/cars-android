package com.paulklauser.cars.models

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.paulklauser.cars.ui.theme.CarsTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ModelsRoute() {
    val vm = hiltViewModel<ModelsViewModel>()
    val uiState by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.fetchIfNeeded()
    }
    ModelsScreen(uiState)
}

@Composable
fun ModelsScreen(uiState: ModelsUiState) {
    LazyColumn {
        items(uiState.models) { model ->
            ModelRow(
                model = model,
                onClick = {
                    // TODO: PK
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ModelsScreenPreview() {
    CarsTheme {
        ModelsScreen(
            ModelsUiState(
                models = persistentListOf(
                    Model(id = "1", name = "Model 1", makeId = "1"),
                    Model(id = "2", name = "Model 2", makeId = "2"),
                    Model(id = "3", name = "Model 3", makeId = "3")
                )
            )
        )
    }
}