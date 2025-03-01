package com.tg.android.fetchce.data.remote

/**
 * Sealed class to represent limited states of network calls
 */
sealed class NetworkResult<T> {
    class Success<T>(val data: T) : NetworkResult<T>()
    class Error<T>(val message: String) : NetworkResult<T>()
    class Loading<T> : NetworkResult<T>()
}