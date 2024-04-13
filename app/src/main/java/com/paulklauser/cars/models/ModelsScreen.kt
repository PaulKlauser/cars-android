package com.paulklauser.cars.models

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
    ModelsScreen(uiState)
}

@Composable
fun ModelsScreen(uiState: ModelsUiState) {
    LazyColumn {
        items(uiState.models) { model ->
            ModelRow(model = model)
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
                    Model("1", "Model 1"),
                    Model("2", "Model 2"),
                    Model("3", "Model 3")
                )
            )
        )
    }
}