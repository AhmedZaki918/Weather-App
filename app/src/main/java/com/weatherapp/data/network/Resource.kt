package com.weatherapp.data.network

import okhttp3.ResponseBody

// To handle all api response
sealed class Resource<out T> {
    data object Idle : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()
}
