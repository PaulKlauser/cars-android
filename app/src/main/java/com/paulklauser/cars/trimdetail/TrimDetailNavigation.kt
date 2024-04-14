package com.paulklauser.cars.trimdetail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val TRIM_DETAIL_ROUTE_PATTERN = "trim_detail"
const val TRIM_ID_PATTERN = "trim_id"

fun NavGraphBuilder.trimDetail(onNavigateBack: () -> Unit) {
    composable("$TRIM_DETAIL_ROUTE_PATTERN/{$TRIM_ID_PATTERN}") {
        TrimDetailRoute(onNavigateBack = onNavigateBack)
    }
}