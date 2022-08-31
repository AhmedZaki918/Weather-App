package com.weatherapp.data.repository

import com.weatherapp.data.local.Constants.API_KEY
import com.weatherapp.data.network.APIService
import com.weatherapp.data.network.SafeApiCall
import javax.inject.Inject

class HomeRepo @Inject constructor(private val api: APIService) : SafeApiCall {


    suspend fun getCurrentWeather() = safeApiCall {
        api.getCurrentWeather(
            API_KEY,
            30.3162878,
            31.73694101738903,
            "metric",
        )
    }

    suspend fun getFiveDaysForecast() = safeApiCall {
        api.getWeatherForecast(
            API_KEY,
            30.3162878,
            31.73694101738903,
            9,
            "metric",
        )
    }
}