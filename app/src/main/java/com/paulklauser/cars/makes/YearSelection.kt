package com.paulklauser.cars.makes

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.paulklauser.cars.commonapi.Year

@Composable
fun YearSelection(
    selectedYear: Year,
    onYearSelected: (Year) -> Unit
) {
    Box {
        var expanded by remember { mutableStateOf(false) }
        val selectedInternal: (Year) -> Unit = { year ->
            expanded = false
            onYearSelected(year)
        }
        TextButton(onClick = { expanded = true }) {
            Text(text = selectedYear.value)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            YearItem(year = Year.TWENTY_FIFTEEN, onYearSelected = selectedInternal)
            YearItem(year = Year.TWENTY_SIXTEEN, onYearSelected = selectedInternal)
            YearItem(year = Year.TWENTY_SEVENTEEN, onYearSelected = selectedInternal)
            YearItem(year = Year.TWENTY_EIGHTEEN, onYearSelected = selectedInternal)
            YearItem(year = Year.TWENTY_NINETEEN, onYearSelected = selectedInternal)
            YearItem(year = Year.TWENTY_TWENTY, onYearSelected = selectedInternal)
        }
    }
}

@Composable
private fun YearItem(year: Year, onYearSelected: (Year) -> Unit) {
    DropdownMenuItem(text = { Text(text = year.value) }, onClick = { onYearSelected(year) })
}