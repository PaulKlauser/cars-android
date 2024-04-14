package com.paulklauser.cars.trimdetail

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
fun TrimDetailRoute() {
    val vm = hiltViewModel<TrimDetailViewModel>()
    val uiState by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.fetchTrimDetail()
    }
    TrimDetailScreen(uiState = uiState)
}

@Composable
fun TrimDetailScreen(uiState: TrimDetailUiState) {
    Column {
        Text(text = uiState.trimDetail.year)
        Text(text = uiState.trimDetail.description)
        Text(text = uiState.trimDetail.msrp)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TrimDetailScreenPreview() {
    CarsTheme {
        TrimDetailScreen(
            uiState = TrimDetailUiState(
                trimDetail = TrimDetail(
                    year = "2022",
                    description = "This is a car",
                    msrp = "$100,000"
                )
            )
        )
    }
}