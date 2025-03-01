package com.tg.android.fetchce.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tg.android.fetchce.data.model.Item
import com.tg.android.fetchce.domain.model.GroupedItems
import com.tg.android.fetchce.ui.components.ItemRow
import com.tg.android.fetchce.ui.components.AppTopBar
import com.tg.android.fetchce.viewmodels.ItemViewModel
import com.tg.android.fetchce.viewmodels.ItemsUiState
import com.tg.android.fetchce.ui.components.FilterCard

/**
 * Main home screen composable that displays the item list with filtering options
 */
@Composable
fun HomeScreen(viewModel: ItemViewModel = viewModel()) {
    // Collect state from ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val currentGroupId by viewModel.currListId.collectAsState()
    val sortOption by viewModel.sortBy.collectAsState()

    // Structure for screen
    Scaffold(
        topBar = { AppTopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            FilterCard(
                currentGroupId = currentGroupId,
                sortOption = sortOption,
                groupedItems = (uiState as? ItemsUiState.Success)?.data.orEmpty(),
                onGroupSelected = { viewModel.setCurrentGroupId(it) },
                onSortChanged = { viewModel.setSortOption(it) }
            )

            ContentArea(uiState = uiState, currentGroupId = currentGroupId)
        }
    }
}


/**
 * Content area that handles different UI states
 */
@Composable
private fun ContentArea(
    uiState: ItemsUiState,
    currentGroupId: Int
) {
    when (uiState) {
        is ItemsUiState.Loading -> {
            LoadingState()
        }

        is ItemsUiState.Error -> {
            ErrorState(message = uiState.message)
        }

        is ItemsUiState.Success -> {
            SuccessState(
                groupedItems = uiState.data,
                currentGroupId = currentGroupId
            )
        }
    }
}

/**
 * Loading state component
 */
@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Error state component
 */
@Composable
private fun ErrorState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
    }
}

/**
 * Success state component displaying items
 */
@Composable
private fun SuccessState(
    groupedItems: List<GroupedItems>,
    currentGroupId: Int
) {
    val selectedGroup = groupedItems.firstOrNull { it.listId == currentGroupId }

    if (selectedGroup != null) {
        Column {
            Text(
                "Showing ${selectedGroup.items.size} items",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            ItemList(items = selectedGroup.items)
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No items were found for the selected group!")
        }
    }
}

/**
 * Item list component
 */
@Composable
private fun ItemList(items: List<Item>) {
    if (items.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No items to display")
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ItemRow(item)
            }
        }
    }
}