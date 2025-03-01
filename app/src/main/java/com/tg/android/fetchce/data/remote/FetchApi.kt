package com.tg.android.fetchce.data.remote

import com.tg.android.fetchce.data.model.Item
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit API interface defining the endpoints
 */
interface FetchApi {

    /**
     * Gets the list of items from the "hiring.json" endpoint
     */
    @GET("hiring.json")
    suspend fun getItems(): Response<List<Item>>
}
