package com.tg.android.fetchce.di

import FetchApi
import com.tg.android.fetchce.data.repository.ItemRepository
import com.tg.android.fetchce.domain.repository.IItemRepository
import com.tg.android.fetchce.domain.usecase.GetItemsUseCase
import com.tg.android.fetchce.domain.usecase.SortItemsUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Object that provides dependencies related to networking
 *
 * I was going to use Hilt to handle dependency injection
 * but it is a simple application
 */
object NetworkModule {
    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    // Provides instance of OkHttpClient with logging capabilities
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    // Provides instance of retrofit configured with okHTTPClient
    // To make API calls and convert JSON to kotlin objects
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provides implementation of FetchApi
    // Rely on Retrofit's auto generated implementation of API interface
    fun provideFetchApi(retrofit: Retrofit): FetchApi {
        return retrofit.create(FetchApi::class.java)
    }

    // Provides instance of repo to interact with API and process data
    fun provideItemRepository(fetchApi: FetchApi): IItemRepository {
        return ItemRepository(fetchApi)
    }

    // UseCases contain business logic
    // Provides instance of GetItemsUseCase
    fun provideGetItemsUseCase(repository: IItemRepository): GetItemsUseCase {
        return GetItemsUseCase(repository)
    }

    // Provides instance of SortItemsUseCase
    fun provideSortItemsUseCase(): SortItemsUseCase {
        return SortItemsUseCase()
    }
}