package com.paulklauser.cars.models

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ModelRow(model: Model) {
    Text(text = model.name)
}