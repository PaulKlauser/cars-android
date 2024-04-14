package com.paulklauser.cars.modeldetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.paulklauser.cars.ui.theme.CarsTheme

@Composable
fun ModelDetailRoute() {
    val vm = hiltViewModel<ModelDetailViewModel>()
    val uiState by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.fetchModelDetail()
    }
    ModelDetailScreen(uiState = uiState)
}

@Composable
fun ModelDetailScreen(uiState: ModelDetailUiState) {
    Column {
        Text(text = uiState.modelDetail.year)
        Text(text = uiState.modelDetail.description)
        Text(text = uiState.modelDetail.msrp)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ModelDetailScreenPreview() {
    CarsTheme {
        ModelDetailScreen(
            uiState = ModelDetailUiState(
                modelDetail = ModelDetail(
                    year = "2022",
                    description = "This is a car",
                    msrp = "$100,000"
                )
            )
        )
    }
}