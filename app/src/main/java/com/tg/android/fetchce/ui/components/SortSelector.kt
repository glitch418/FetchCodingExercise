package com.tg.android.fetchce.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tg.android.fetchce.domain.model.ItemSortOption

/**
 * Button to choose specific sorting of items to display
 * Uses blue button with white text for better visibility
 */
@Composable
fun SortSelector(
    modifier: Modifier = Modifier,
    selectedOption: ItemSortOption,
    onSortChanged: (ItemSortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFF6A0DAD),
                contentColor = Color.White
            )
        ) {
            Text(
                text = when (selectedOption) {
                    ItemSortOption.ALPHABETICAL -> "Alphabetical"
                    ItemSortOption.REVERSE_ALPHABETICAL -> "Reverse Alphabetical"
                    ItemSortOption.NUMERICAL -> "Numerical"
                    ItemSortOption.REVERSE_NUMERICAL -> "Reverse Numerical"
                },
                color = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentWidth()
        ) {
            ItemSortOption.values().forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = when (option) {
                                ItemSortOption.ALPHABETICAL -> "Alphabetical"
                                ItemSortOption.REVERSE_ALPHABETICAL -> "Reverse Alphabetical"
                                ItemSortOption.NUMERICAL -> "Numerical"
                                ItemSortOption.REVERSE_NUMERICAL -> "Reverse Numerical"
                            }
                        )
                    },
                    onClick = {
                        onSortChanged(option)
                        expanded = false
                    }
                )
            }
        }
    }
}