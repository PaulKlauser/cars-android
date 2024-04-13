package com.paulklauser.cars.makes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paulklauser.cars.ui.theme.CarsTheme

@Composable
fun MakeRow(make: Make, onClick: (String) -> Unit) {
    Card(
        onClick = { onClick(make.id) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = make.name,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MakeRowPreview() {
    CarsTheme {
        MakeRow(make = Make(id = "1", name = "Toyota"), onClick = {})
    }
}