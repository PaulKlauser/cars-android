package com.paulklauser.cars.makes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MakeRow(make: Make) {
    Text(text = make.name)
}