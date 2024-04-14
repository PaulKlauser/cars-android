package com.paulklauser.cars.models

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.paulklauser.cars.Loading
import com.paulklauser.cars.ui.theme.CarsTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ModelsRoute(
    onNavigateBack: () -> Unit,
    onTrimSelected: (String) -> Unit
) {
    val vm = hiltViewModel<ModelsViewModel>()
    val uiState by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.fetchIfNeeded()
    }
    ModelsScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onTrimSelected = onTrimSelected
    )
}

@Composable
fun ModelsScreen(
    uiState: ModelsUiState,
    onNavigateBack: () -> Unit,
    onTrimSelected: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = (uiState.loadingState as?
                                ModelsUiState.LoadingState.Success)?.make ?: ""
                    )
                },
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
        Box(modifier = Modifier.padding(paddingValues)) {
            when (uiState.loadingState) {
                ModelsUiState.LoadingState.Loading -> Loading(
                    modifier = Modifier.fillMaxSize()
                )

                is ModelsUiState.LoadingState.Success -> Loaded(
                    models = uiState.loadingState.models,
                    onTrimSelected = onTrimSelected
                )
            }
        }
    }
}

@Composable
private fun Loaded(
    models: ImmutableList<ModelRowItem>,
    onTrimSelected: (String) -> Unit
) {
    LazyColumn {
        items(models) { model ->
            var expanded by rememberSaveable { mutableStateOf(false) }
            ModelRow(
                item = model,
                onClick = { expanded = !expanded },
                expanded = expanded,
                onTrimSelected = onTrimSelected
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ModelsScreenPreview() {
    CarsTheme {
        ModelsScreen(
            uiState = ModelsUiState(
                loadingState = ModelsUiState.LoadingState.Success(
                    models = persistentListOf(
                        ModelRowItem(
                            model = Model(
                                id = "1",
                                name = "M3",
                                makeId = "1"
                            ),
                            trims = listOf(
                                Trim(
                                    id = "1",
                                    description = "LE"
                                ),
                                Trim(
                                    id = "2",
                                    description = "SE"
                                )
                            )
                        ),
                        ModelRowItem(
                            model = Model(
                                id = "2",
                                name = "M5",
                                makeId = "1"
                            ),
                            trims = listOf(
                                Trim(
                                    id = "3",
                                    description = "LE"
                                ),
                                Trim(
                                    id = "4",
                                    description = "SE"
                                )
                            )
                        )
                    ),
                    make = "BMW"
                )
            ),
            onNavigateBack = { },
            onTrimSelected = { }
        )
    }
}