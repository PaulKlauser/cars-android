package com.paulklauser.cars.trimdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paulklauser.cars.R
import com.paulklauser.cars.ui.theme.CarsTheme
import com.paulklauser.cars.ui.theme.Green

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
                    uiState.loadingState.trimDetail?.let {
                        Text(text = "${it.year} ${it.make} ${it.model}")
                    }
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
        Box(modifier = Modifier.padding(paddingValues)) {
            when (uiState.loadingState) {
                TrimDetailUiState.LoadingState.Loading -> Loading(modifier = Modifier.fillMaxSize())
                is TrimDetailUiState.LoadingState.Success -> Loaded(uiState.loadingState.trimDetail)
            }
        }
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Loaded(trimDetail: TrimDetail) {
    Column {
        Text(
            text = trimDetail.description,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
        )
        DetailCard(
            label = stringResource(R.string.msrp),
            value = trimDetail.msrp,
            modifier = Modifier
                .fillMaxWidth()
        )
        FuelEconomy(
            cityMpg = trimDetail.cityMpg,
            highwayMpg = trimDetail.highwayMpg,
            combinedMpg = trimDetail.combinedMpg,
            modifier = Modifier.fillMaxWidth()
        )
        DetailCard(title = stringResource(R.string.engine), modifier = Modifier.fillMaxWidth()) {
            val horsePower = buildAnnotatedString {
                withStyle(MaterialTheme.typography.labelLarge.toSpanStyle()) {
                    append(stringResource(R.string.power))
                }
                withStyle(MaterialTheme.typography.bodyLarge.toSpanStyle()) {
                    append(" ")
                    append(stringResource(R.string.hp, trimDetail.horsepower))
                }
            }
            val torque = buildAnnotatedString {
                withStyle(MaterialTheme.typography.labelLarge.toSpanStyle()) {
                    append(stringResource(R.string.torque))
                }
                withStyle(MaterialTheme.typography.bodyLarge.toSpanStyle()) {
                    append(" ")
                    append(stringResource(R.string.lb_ft, trimDetail.torque))
                }
            }
            Column {
                Text(text = horsePower)
                Text(text = torque)
            }
        }
    }
}

@Composable
private fun DetailCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, style = MaterialTheme.typography.labelLarge)
            Text(text = value)
        }
    }
}

@Composable
private fun DetailCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(modifier = modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            content()
        }
    }
}

@Composable
private fun FuelEconomy(
    cityMpg: String,
    highwayMpg: String,
    combinedMpg: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.fuel_economy),
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = highwayMpg, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(R.string.highway))
            }
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = cityMpg, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(R.string.city))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = combinedMpg,
                    style = MaterialTheme.typography.titleLarge,
                    color = Green
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.combined),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TrimDetailScreenPreview(@PreviewParameter(PreviewProvider::class) uiState: TrimDetailUiState) {
    CarsTheme {
        TrimDetailScreen(
            uiState = uiState,
            onNavigateBack = {}
        )
    }
}

private class PreviewProvider : CollectionPreviewParameterProvider<TrimDetailUiState>(
    listOf(
        TrimDetailUiState(
            loadingState = TrimDetailUiState.LoadingState.Success(
                TrimDetail(
                    year = "2022",
                    make = "Toyota",
                    model = "Corolla",
                    description = "FWD 4dr Sedan (2.0L 4cyl CVT)",
                    msrp = "$100,000",
                    combinedMpg = "33",
                    cityMpg = "31",
                    highwayMpg = "38",
                    horsepower = "169",
                    torque = "151"
                )
            )
        ),
        TrimDetailUiState(
            loadingState = TrimDetailUiState.LoadingState.Loading
        )
    )
)