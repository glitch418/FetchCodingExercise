package com.tg.android.fetchce.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tg.android.fetchce.domain.model.GroupedItems
import com.tg.android.fetchce.domain.model.ItemSortOption

/**
 * Filter card component containing group and sort selectors
 */
@Composable
fun FilterCard(
    currentGroupId: Int,
    sortOption: ItemSortOption,
    groupedItems: List<GroupedItems>,
    onGroupSelected: (Int) -> Unit,
    onSortChanged: (ItemSortOption) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB6962B)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterSelectorRow(
                labelText = "Select Group:",
                content = {
                    GroupSelector(
                        modifier = Modifier.weight(1f),
                        currentGroupId = currentGroupId,
                        groupedItems = groupedItems,
                        onGroupSelected = onGroupSelected
                    )
                }
            )

            FilterSelectorRow(
                labelText = "Sort By:",
                content = {
                    SortSelector(
                        modifier = Modifier.weight(1f),
                        selectedOption = sortOption,
                        onSortChanged = onSortChanged
                    )
                }
            )
        }
    }
}

/**
 * Row component for filter selectors
 */
@Composable
fun FilterSelectorRow(
    labelText: String,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = labelText,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .width(100.dp)
                .padding(end = 8.dp),
            color = Color(0xFF4E0980)
        )
        content()
    }
}