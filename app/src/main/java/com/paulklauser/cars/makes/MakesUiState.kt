package com.paulklauser.cars.makes

import com.paulklauser.cars.commonapi.Year
import kotlinx.collections.immutable.ImmutableList

data class MakesUiState(
    val makes: ImmutableList<Make>,
    val selectedYear: Year
)
