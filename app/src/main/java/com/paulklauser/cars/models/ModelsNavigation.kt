package com.paulklauser.cars.models

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val MODELS_ROUTE_PATTERN = "models"
const val MAKE_ID_PATTERN = "makeId"

fun NavGraphBuilder.models(
    onNavigateBack: () -> Unit,
    onModelSelected: (String) -> Unit
) {
    composable("$MODELS_ROUTE_PATTERN/{$MAKE_ID_PATTERN}") {
        ModelsRoute(
            onNavigateBack = onNavigateBack,
            onModelSelected = onModelSelected
        )
    }
}