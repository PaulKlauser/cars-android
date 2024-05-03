package com.paulklauser.cars.models

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val MODELS_ROUTE_PATTERN = "models"
const val MAKE_ID_PATTERN = "makeId"

class ModelsArgs(val makeId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        makeId = savedStateHandle[MAKE_ID_PATTERN]
            ?: throw IllegalArgumentException("Make ID not provided!")
    )
}

fun NavController.navigateToModel(makeId: String) {
    navigate("$MODELS_ROUTE_PATTERN/$makeId")
}

fun NavGraphBuilder.models(
    onNavigateBack: () -> Unit,
    onTrimSelected: (String) -> Unit
) {
    composable("$MODELS_ROUTE_PATTERN/{$MAKE_ID_PATTERN}") {
        ModelsRoute(
            onNavigateBack = onNavigateBack,
            onTrimSelected = onTrimSelected
        )
    }
}