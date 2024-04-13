package com.paulklauser.cars.models

import kotlinx.collections.immutable.ImmutableList

data class ModelsUiState(
    val models: ImmutableList<Model>,
    val make: String
)
