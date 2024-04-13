package com.paulklauser.cars.models

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.paulklauser.cars.ui.theme.CarsTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ModelsRoute(
    onNavigateBack: () -> Unit,
    onModelSelected: (String) -> Unit
) {
    val vm = hiltViewModel<ModelsViewModel>()
    val uiState by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.fetchIfNeeded()
    }
    ModelsScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onModelSelected = onModelSelected
    )
}

@Composable
fun ModelsScreen(
    uiState: ModelsUiState,
    onNavigateBack: () -> Unit,
    onModelSelected: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.make) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues = paddingValues)) {
            items(uiState.models) { model ->
                ModelRow(
                    model = model,
                    onClick = onModelSelected
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ModelsScreenPreview() {
    CarsTheme {
        ModelsScreen(
            uiState = ModelsUiState(
                models = persistentListOf(
                    Model(id = "1", name = "Model 1", makeId = "1"),
                    Model(id = "2", name = "Model 2", makeId = "2"),
                    Model(id = "3", name = "Model 3", makeId = "3")
                ),
                make = "BMW"
            ),
            onNavigateBack = { },
            onModelSelected = { }
        )
    }
}