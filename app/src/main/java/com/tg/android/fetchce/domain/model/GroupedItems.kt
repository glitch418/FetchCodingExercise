package com.tg.android.fetchce.domain.model

import com.tg.android.fetchce.data.model.Item

/**
 * Represents a group of items with the same listId
 */
data class GroupedItems(
    val listId: Int,
    val items: List<Item>
)
