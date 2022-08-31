package com.weatherapp.data.network

import okhttp3.ResponseBody

// To handle all api response
sealed class Resource<out T> {
    object Idle : Resource<Nothing>()
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()
}
