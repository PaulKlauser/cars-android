package com.paulklauser.cars.trimdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.paulklauser.cars.R
import com.paulklauser.cars.ui.theme.CarsTheme

@Composable
fun TrimDetailRoute(onNavigateBack: () -> Unit) {
    val vm = hiltViewModel<TrimDetailViewModel>()
    val uiState by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.fetchTrimDetail()
    }
    TrimDetailScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack
    )
}

@Composable
fun TrimDetailScreen(
    uiState: TrimDetailUiState,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "${uiState.trimDetail.year} ${uiState.trimDetail.make} ${uiState.trimDetail.model}")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(text = uiState.trimDetail.year)
            Text(text = uiState.trimDetail.description)
            Text(text = uiState.trimDetail.msrp)
        }
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
                    make = "Toyota",
                    model = "Corolla",
                    description = "This is a car",
                    msrp = "$100,000"
                )
            ),
            onNavigateBack = {}
        )
    }
}