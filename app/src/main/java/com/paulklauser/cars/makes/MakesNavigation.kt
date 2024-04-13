package com.paulklauser.cars.makes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val MAKES_ROUTE = "makes"

fun NavGraphBuilder.makes(
    onMakeSelected: (String) -> Unit
) {
    composable(MAKES_ROUTE) {
        MakesScreenRoute(
            onMakeSelected = onMakeSelected
        )
    }
}