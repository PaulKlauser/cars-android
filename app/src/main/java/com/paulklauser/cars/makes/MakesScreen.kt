package com.paulklauser.cars.makes

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.paulklauser.cars.Error
import com.paulklauser.cars.Loading
import com.paulklauser.cars.commonapi.Year
import com.paulklauser.cars.ui.theme.CarsTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MakesScreenRoute(onMakeSelected: (String) -> Unit) {
    val vm = hiltViewModel<MakesViewModel>()
    val uiState by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.fetchMakes()
    }
    MakesScreen(
        uiState = uiState,
        onMakeSelected = onMakeSelected,
        onYearSelected = vm::onYearSelected,
        vm::fetchMakes
    )
}

@Composable
fun MakesScreen(
    uiState: MakesUiState,
    onMakeSelected: (String) -> Unit,
    onYearSelected: (Year) -> Unit,
    onRetry: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Choose a make") },
                actions = {
                    YearSelection(
                        selectedYear = uiState.selectedYear,
                        onYearSelected = onYearSelected
                    )
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
                    MakesUiState.LoadingState.Loading -> Loading(
                        modifier = Modifier.fillMaxSize()
                    )

                    is MakesUiState.LoadingState.Success -> Loaded(
                        it.makes,
                        onMakeSelected
                    )

                    MakesUiState.LoadingState.Error -> Error(
                        onRetry = onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun Loaded(makes: ImmutableList<Make>, onMakeSelected: (String) -> Unit) {
    LazyColumn(contentPadding = WindowInsets.navigationBars.asPaddingValues()) {
        items(makes) { make ->
            MakeRow(make = make, onClick = onMakeSelected)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MakesScreenPreview() {
    CarsTheme {
        MakesScreen(
            uiState = MakesUiState(
                loadingState = MakesUiState.LoadingState.Success(
                    makes = persistentListOf(
                        Make(id = "1", "Toyota"),
                        Make(id = "2", "Ford"),
                        Make(id = "3", "Chevrolet")
                    )
                ),
                selectedYear = Year.TWENTY_FIFTEEN
            ),
            onMakeSelected = {},
            onYearSelected = {},
            onRetry = {}
        )
    }
}