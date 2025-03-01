package com.tg.android.fetchce.domain.usecase

import com.tg.android.fetchce.data.model.Item
import com.tg.android.fetchce.data.remote.NetworkResult
import com.tg.android.fetchce.domain.model.GroupedItems
import com.tg.android.fetchce.domain.repository.IItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case that retrieves the items from the repository and
 * Performs required operations (filtering, sorting, and grouping)
 */
class GetItemsUseCase(
    private val repository: IItemRepository
) {
    /**
     * Invokes the use case to return the modified items
     */
    operator fun invoke(): Flow<NetworkResult<List<GroupedItems>>> {
        // Observe stream and transform result
        return repository.getItems().map { result ->
            when (result) {
                is NetworkResult.Success -> {

                    // Perform operations
                    val groupedItems = result.data
                        // Remove items with blank or null names
                        .filter { it.name?.isNotBlank() == true }
                        // Sort by listId, then by name
                        .sortedWith(compareBy<Item> { it.listId }.thenBy { it.name })
                        // Group by listId
                        .groupBy { it.listId }
                        // Convert to type: GroupedItems
                        .map { (listId, items) -> GroupedItems(listId, items) }

                    NetworkResult.Success(groupedItems)
                }

                is NetworkResult.Error -> NetworkResult.Error(result.message)
                is NetworkResult.Loading -> NetworkResult.Loading()
            }
        }
    }
}