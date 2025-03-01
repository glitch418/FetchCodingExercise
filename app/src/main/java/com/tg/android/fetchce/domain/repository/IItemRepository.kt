package com.tg.android.fetchce.domain.repository

import com.tg.android.fetchce.data.model.Item
import com.tg.android.fetchce.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the rules for data operations
 */
interface IItemRepository {

    fun getItems(): Flow<NetworkResult<List<Item>>>
}