package com.tg.android.fetchce.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tg.android.fetchce.domain.model.GroupedItems

/**
 * Button to choose specific group of items to display
 */
@Composable
fun GroupSelector(
    modifier: Modifier = Modifier,
    currentGroupId: Int,
    groupedItems: List<GroupedItems>,
    onGroupSelected: (Int) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFF6A0DAD),
                contentColor = Color.White
            )
        ) {
            Text("$currentGroupId", color = Color.White)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentWidth()
        ) {
            groupedItems.forEach { group ->
                DropdownMenuItem(
                    text = { Text("${group.listId}") },
                    onClick = {
                        onGroupSelected(group.listId)
                        expanded = false
                    }
                )
            }
        }
    }
}
