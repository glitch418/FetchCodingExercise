package com.tg.android.fetchce.domain.usecase

import com.tg.android.fetchce.data.remote.NetworkResult
import com.tg.android.fetchce.domain.model.GroupedItems
import com.tg.android.fetchce.domain.model.ItemSortOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Use case to handle sorting of items based on different criteria
 */
class SortItemsUseCase {
    /**
     * Invokes use case to sort based on provided option
     * @param data list of grouped items to sort
     * @param sortOption sorting option to apply
     * @return flow with sorted list of grouped items
     */
    operator fun invoke(
        data: List<GroupedItems>,
        sortOption: ItemSortOption
    ): Flow<NetworkResult<List<GroupedItems>>> = flow {

        emit(NetworkResult.Loading())

        // Try performing required operations
        try {
            if (data.isEmpty()) {
                emit(NetworkResult.Success(emptyList()))
                // Terminate
                return@flow
            }

            // Sort Grouped Items and store in variable
            val sortedData = data.map { groupedItems ->
                val sortedItems = when (sortOption) {

                    // Lexicographic comparison is used
                    // Item 2 will appear before Item 300;  Reason: "2" < "3" alphabetically ASC
                    ItemSortOption.ALPHABETICAL -> {
                        groupedItems.items.sortedBy { it.name }
                    }

                    // Item 2 will appear before Item 1;    Reason: "2" > "1" alphabetically DESC
                    ItemSortOption.REVERSE_ALPHABETICAL -> {
                        groupedItems.items.sortedByDescending { it.name }
                    }

                    // Numerical comparison is used between numerical component only
                    // Id numbers and Name numbers are the same in dataset. This is a special case.
                    // Sorting is being done based on Id Numbers here
                    // To sort based on Name Numbers, replace it.id with extractNumericPart function

                    // Item 1 will appear before Item 2;    Reason: 1 < 2 numerically ASC
                    ItemSortOption.NUMERICAL -> {
                        groupedItems.items.sortedBy {
                            // extractNumericPart(it.name ?: "") ?: Integer.MAX_VALUE
                            it.id
                        }
                    }

                    // Item 2 will appear before Item 1;    Reason: 2 > 1 numerically DESC
                    ItemSortOption.REVERSE_NUMERICAL -> {
                        groupedItems.items.sortedByDescending {
                            // extractNumericPart(it.name ?: "") ?: Integer.MIN_VALUE
                            it.id
                        }
                    }
                }

                // Data classes are immutable,
                // copy() is used to make sure we don't modify original data
                groupedItems.copy(items = sortedItems)
            }

            emit(NetworkResult.Success(sortedData))
        // Try Block Failed
        } catch (e: Exception) {
            emit(NetworkResult.Error("Error sorting items: ${e.message}"))
        }
    }

    /**
     * Extracts the first sequence of digits in the string
     * @param name Item name
     * @return first sequence of numerical part, if any
     * Example use case: "Item 123", returns 123
     */
    private fun extractNumericPart(name: String): Int? {
        val regex = Regex("\\d+")
        val matchResult = regex.find(name)

        return matchResult?.value?.toIntOrNull()
    }
}