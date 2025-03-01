package com.tg.android.fetchce.data.repository

import com.tg.android.fetchce.data.model.Item
import com.tg.android.fetchce.data.remote.FetchApi
import com.tg.android.fetchce.data.remote.NetworkResult
import com.tg.android.fetchce.domain.repository.IItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Fetches data from the API
 * Can provide data to use cases
 */
class ItemRepository(
    private val fetchApi: FetchApi
) : IItemRepository {

    /**
     * Gets items from the API
     */
    override fun getItems(): Flow<NetworkResult<List<Item>>> = flow {
        emit(NetworkResult.Loading())

        try {
            val response = fetchApi.getItems()
            if (response.isSuccessful) {
                val items = response.body() ?: emptyList()
                emit(NetworkResult.Success(items))
            } else {
                emit(NetworkResult.Error("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error("Error: ${e.message}"))
        }
    }
}