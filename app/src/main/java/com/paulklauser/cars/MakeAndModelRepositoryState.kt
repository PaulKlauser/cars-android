package com.paulklauser.cars

import com.paulklauser.cars.makes.Make
import com.paulklauser.cars.models.Model

data class MakeAndModelRepositoryState(
    val makesToModels: Map<Make, List<Model>>
)