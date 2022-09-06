package com.weatherapp.data.repository

import com.weatherapp.R
import com.weatherapp.data.local.Constants.API_KEY
import com.weatherapp.data.model.FiveDaysDummy
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


    fun displayDummyData(): List<FiveDaysDummy> {
        return listOf(
            FiveDaysDummy("2022-09-04", "12:00", R.drawable.preview_cloudy, "Clear", "30°"),
            FiveDaysDummy("2022-09-04", "15:00", R.drawable.preview_cloudy, "Sunny", "33°"),
            FiveDaysDummy("2022-09-04", "18:00", R.drawable.preview_cloudy, "Cloudy", "29°"),
            FiveDaysDummy("2022-09-04", "21:00", R.drawable.preview_cloudy, "Clear", "26°"),
            FiveDaysDummy("2022-09-05", "00:00", R.drawable.preview_cloudy, "Clear", "22°"),
            FiveDaysDummy("2022-09-05", "03:00", R.drawable.preview_cloudy, "Rainy", "18°"),
            FiveDaysDummy("2022-09-05", "06:00", R.drawable.preview_cloudy, "Sunny", "22°"),
            FiveDaysDummy("2022-09-05", "09:00", R.drawable.preview_cloudy, "Clear", "26°"),
            FiveDaysDummy("2022-09-05", "12:00", R.drawable.preview_cloudy, "Clear", "29°"),
        )
    }
}