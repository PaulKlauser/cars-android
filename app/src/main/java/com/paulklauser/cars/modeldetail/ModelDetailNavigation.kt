package com.paulklauser.cars.modeldetail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val MODEL_DETAIL_ROUTE_PATTERN = "model_detail"
const val MODEL_ID_PATTERN = "model_id"

fun NavGraphBuilder.modelDetail() {
    composable("$MODEL_DETAIL_ROUTE_PATTERN/{$MODEL_ID_PATTERN}") {
        ModelDetailRoute()
    }
}