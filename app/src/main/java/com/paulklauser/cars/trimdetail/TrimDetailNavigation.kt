package com.paulklauser.cars.trimdetail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val TRIM_DETAIL_ROUTE_PATTERN = "trim_detail"
const val TRIM_ID_PATTERN = "trim_id"

class TrimDetailArgs(val trimId: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        trimId = savedStateHandle[TRIM_ID_PATTERN]
            ?: throw IllegalArgumentException("Trim ID not provided!")
    )
}

fun NavController.navigateToTrimDetail(trimId: String) {
    navigate("$TRIM_DETAIL_ROUTE_PATTERN/$trimId")
}

fun NavGraphBuilder.trimDetail(onNavigateBack: () -> Unit) {
    composable("$TRIM_DETAIL_ROUTE_PATTERN/{$TRIM_ID_PATTERN}") {
        TrimDetailRoute(onNavigateBack = onNavigateBack)
    }
}