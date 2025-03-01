package com.tg.android.fetchce.data.model

/**
 * Data class representing structure of items from the API
 *
 * @property id unique identifier of item
 * @property listId list id this item belongs to
 * @property name The name of the item, can be null
 */
data class Item(
    val id: Int,
    val listId: Int,
    val name: String?
)
