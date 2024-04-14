package com.paulklauser.cars.models

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paulklauser.cars.R
import com.paulklauser.cars.ui.theme.CarsTheme

@Composable
fun ModelRow(
    item: ModelRowItem,
    onClick: (String) -> Unit,
    expanded: Boolean,
    onTrimSelected: (String) -> Unit
) {
    Card(
        onClick = { onClick(item.model.id) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = item.model.name,
            modifier = Modifier.padding(16.dp)
        )
        if (expanded) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Row {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.available_trims),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                item.trims.forEach { trim ->
                    TextButton(
                        onClick = { onTrimSelected(trim.id) },
                    ) {
                        Text(text = trim.description)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ModelRowPreview() {
    CarsTheme {
        ModelRow(
            item = ModelRowItem(
                model = Model(
                    id = "1",
                    name = "Corolla",
                    makeId = "1"
                ),
                trims = listOf(
                    Trim(
                        id = "1",
                        description = "LE"
                    )
                )
            ),
            onClick = {},
            expanded = false,
            onTrimSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ModelRowPreviewExpanded() {
    CarsTheme {
        ModelRow(
            item = ModelRowItem(
                model = Model(
                    id = "1",
                    name = "Corolla",
                    makeId = "1"
                ),
                trims = listOf(
                    Trim(
                        id = "1",
                        description = "LE"
                    ),
                    Trim(
                        id = "2",
                        description = "SE"
                    ),
                    Trim(
                        id = "3",
                        description = "XSE"
                    ),
                    Trim(
                        id = "4",
                        description = "XLE"
                    ),
                    Trim(
                        id = "5",
                        description = "Hybrid"
                    ),
                    Trim(
                        id = "6",
                        description = "XSE Hybrid"
                    ),
                    Trim(
                        id = "7",
                        description = "XLE Hybrid Really long name so long it goes off the screen " +
                                "and wraps to the next line and keeps on going"
                    ),
                    Trim(
                        id = "7",
                        description = "XLE Hybrid Really long name so long it goes off the screen " +
                                "and wraps to the next line and keeps on going"
                    )
                )
            ),
            onClick = {},
            expanded = true,
            onTrimSelected = {}
        )
    }
}