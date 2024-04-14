package com.paulklauser.cars.models

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.paulklauser.cars.Error
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
        onTrimSelected = onTrimSelected,
        onRetry = vm::fetchIfNeeded
    )
}

@Composable
fun ModelsScreen(
    uiState: ModelsUiState,
    onNavigateBack: () -> Unit,
    onTrimSelected: (String) -> Unit,
    onRetry: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AnimatedContent(
                        targetState = uiState.make,
                        transitionSpec = { fadeIn().togetherWith(fadeOut()) }
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AnimatedContent(targetState = uiState.loadingState) {
                when (it) {
                    ModelsUiState.LoadingState.Loading -> Loading(
                        modifier = Modifier.fillMaxSize()
                    )

                    is ModelsUiState.LoadingState.Success -> Loaded(
                        models = it.models,
                        onTrimSelected = onTrimSelected
                    )

                    ModelsUiState.LoadingState.Error -> Error(
                        onRetry = onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun Loaded(
    models: ImmutableList<ModelRowItem>,
    onTrimSelected: (String) -> Unit
) {
    var expandedId by rememberSaveable { mutableStateOf<String?>(null) }
    LazyColumn(contentPadding = WindowInsets.navigationBars.asPaddingValues()) {
        items(models) { model ->
            ModelRow(
                item = model,
                onClick = {
                    expandedId = if (expandedId == it) {
                        null
                    } else {
                        it
                    }
                },
                expanded = expandedId == model.model.id,
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
                    )
                ),
                make = "BMW"
            ),
            onNavigateBack = { },
            onTrimSelected = { },
            onRetry = { }
        )
    }
}