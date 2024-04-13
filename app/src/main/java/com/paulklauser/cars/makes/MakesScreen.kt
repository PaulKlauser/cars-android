package com.paulklauser.cars.makes

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
fun MakesScreenRoute() {
    val vm = hiltViewModel<MakesViewModel>()
    val uiState by vm.uiState.collectAsState()
    MakesScreen(uiState = uiState)
}

@Composable
fun MakesScreen(
    uiState: MakesUiState
) {
    LazyColumn {
        items(uiState.makes) { make ->
            MakeRow(make = make)
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
                    Make("Toyota"),
                    Make("Ford"),
                    Make("Chevrolet")
                )
            )
        )
    }
}