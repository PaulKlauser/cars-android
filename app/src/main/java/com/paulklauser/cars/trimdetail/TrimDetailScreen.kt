package com.paulklauser.cars.trimdetail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paulklauser.cars.Error
import com.paulklauser.cars.Loading
import com.paulklauser.cars.R
import com.paulklauser.cars.ui.theme.CarsTheme
import com.paulklauser.cars.ui.theme.Green

@Composable
fun TrimDetailRoute(onNavigateBack: () -> Unit) {
    val vm = hiltViewModel<TrimDetailViewModel>()
    val uiState by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.fetchTrimDetailIfNecessary()
    }
    TrimDetailScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onRetry = vm::fetchTrimDetailIfNecessary
    )
}

@Composable
fun TrimDetailScreen(
    uiState: TrimDetailUiState,
    onNavigateBack: () -> Unit,
    onRetry: () -> Unit
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
            AnimatedContent(targetState = uiState.loadingState) {
                when (it) {
                    TrimDetailUiState.LoadingState.Loading -> Loading(modifier = Modifier.fillMaxSize())
                    is TrimDetailUiState.LoadingState.Success -> Loaded(it.trimDetail)
                    TrimDetailUiState.LoadingState.Error -> Error(
                        modifier = Modifier.fillMaxSize(),
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}

@Composable
private fun Loaded(trimDetail: TrimDetail) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
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
            title = stringResource(R.string.msrp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = trimDetail.msrp)
        }
        trimDetail.fuelEconomy?.let {
            FuelEconomy(
                cityMpg = it.cityMpg,
                highwayMpg = it.highwayMpg,
                combinedMpg = it.combinedMpg,
                modifier = Modifier.fillMaxWidth()
            )
        }
        DetailCard(title = stringResource(R.string.engine), modifier = Modifier.fillMaxWidth()) {
            val horsePower = buildAnnotatedString {
                withStyle(MaterialTheme.typography.titleMedium.toSpanStyle()) {
                    append(stringResource(R.string.power))
                }
                withStyle(MaterialTheme.typography.bodyLarge.toSpanStyle()) {
                    append(" ")
                    append(stringResource(R.string.hp, trimDetail.horsepower))
                }
            }
            val torque = trimDetail.torque?.let {
                buildAnnotatedString {
                    withStyle(MaterialTheme.typography.titleMedium.toSpanStyle()) {
                        append(stringResource(R.string.torque))
                    }
                    withStyle(MaterialTheme.typography.bodyLarge.toSpanStyle()) {
                        append(" ")
                        append(stringResource(R.string.lb_ft, it))
                    }
                }
            }
            Column {
                Text(text = horsePower)
                torque?.let {
                    Text(text = it)
                }
            }
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
    DetailCard(
        title = stringResource(R.string.fuel_economy),
        modifier = modifier
    ) {
        val highway = buildAnnotatedString {
            withStyle(MaterialTheme.typography.titleMedium.toSpanStyle()) {
                append(highwayMpg)
            }
            append(" ")
            append(stringResource(id = R.string.highway))
        }
        val city = buildAnnotatedString {
            withStyle(MaterialTheme.typography.titleMedium.toSpanStyle()) {
                append(cityMpg)
            }
            append(" ")
            append(stringResource(id = R.string.city))
        }
        val combined = buildAnnotatedString {
            withStyle(MaterialTheme.typography.titleLarge.toSpanStyle().copy(color = Green)) {
                append(combinedMpg)
            }
            withStyle(MaterialTheme.typography.titleLarge.toSpanStyle()) {
                append(" ")
                append(stringResource(id = R.string.combined))
            }
        }
        Text(text = highway)
        Text(text = city)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = combined)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TrimDetailScreenPreview(@PreviewParameter(PreviewProvider::class) uiState: TrimDetailUiState) {
    CarsTheme {
        TrimDetailScreen(
            uiState = uiState,
            onNavigateBack = {},
            onRetry = {}
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
                    fuelEconomy = TrimDetail.FuelEconomy(
                        combinedMpg = "33",
                        cityMpg = "31",
                        highwayMpg = "38"
                    ),
                    horsepower = "169",
                    torque = "151"
                )
            )
        ),
        TrimDetailUiState(
            loadingState = TrimDetailUiState.LoadingState.Loading
        ),
        TrimDetailUiState(
            loadingState = TrimDetailUiState.LoadingState.Error
        )
    )
)