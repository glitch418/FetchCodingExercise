package com.tg.android.fetchce.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tg.android.fetchce.data.model.Item

/**
 * Composable to display the item row
 */
@Composable
fun ItemRow(item: Item) {
    Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Item Name
        Text(
            text = item.name ?: "-",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        // Item Id
        Text(
            text = item.id.toString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(0.25f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemRow() {
    val sampleItem = Item(name = "Item 0", listId = 1, id = 0)
    ItemRow(item = sampleItem)
}
