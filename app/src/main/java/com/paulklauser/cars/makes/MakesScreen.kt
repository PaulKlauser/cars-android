package com.paulklauser.cars.makes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.paulklauser.cars.commonapi.Year
import com.paulklauser.cars.ui.theme.CarsTheme
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
        onYearSelected = vm::onYearSelected
    )
}

@Composable
fun MakesScreen(
    uiState: MakesUiState,
    onMakeSelected: (String) -> Unit,
    onYearSelected: (Year) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Choose a make") },
                actions = {
                    YearSelection(
                        selectedYear = uiState.selectedYear,
                        onYearSelected = onYearSelected
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(uiState.makes) { make ->
                MakeRow(make = make, onClick = onMakeSelected)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MakesScreenPreview() {
    CarsTheme {
        MakesScreen(
            uiState = MakesUiState(
                makes = persistentListOf(
                    Make(id = "1", "Toyota"),
                    Make(id = "2", "Ford"),
                    Make(id = "3", "Chevrolet")
                ),
                selectedYear = Year.TWENTY_FIFTEEN
            ),
            onMakeSelected = {},
            onYearSelected = {}
        )
    }
}