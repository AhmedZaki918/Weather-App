package com.weatherapp.data.repository

import com.weatherapp.data.local.Constants.API_KEY
import com.weatherapp.data.network.APIService
import com.weatherapp.data.network.SafeApiCall
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SearchRepo @Inject constructor(private val api: APIService) : SafeApiCall {

    suspend fun getGeocoding(city: String) = safeApiCall {
        api.getGeocoding(API_KEY, city)
    }

    suspend fun getCurrentWeather(lat: Double, lon: Double) = safeApiCall {
        api.getCurrentWeather(
            API_KEY,
            lat,
            lon,
            "metric",
        )
    }

    suspend fun getFiveDaysForecast(lat: Double, lon: Double) = safeApiCall {
        api.getWeatherForecast(
            API_KEY,
            lat,
            lon,
            9,
            "metric",
        )
    }
}