package com.weatherapp.data.repository

import com.weatherapp.R
import com.weatherapp.data.local.Constants.API_KEY
import com.weatherapp.data.model.TodayWeather
import com.weatherapp.data.network.APIService
import com.weatherapp.data.network.SafeApiCall
import javax.inject.Inject

class HomeRepo @Inject constructor(private val api: APIService) : SafeApiCall {


    suspend fun getCurrentWeather() = safeApiCall {
              api.getCurrentWeather(
                  API_KEY,
                  30.3162878,
                  31.73694101738903,
                  "metric"
              )
    }

    fun displayFakeData(): List<TodayWeather> {
        return listOf(
            TodayWeather(R.drawable.preview_cloudy, "20°", "Now"),
            TodayWeather(R.drawable.preview_cloudy, "23°", "3:00"),
            TodayWeather(R.drawable.preview_cloudy, "25°", "6:00"),
            TodayWeather(R.drawable.preview_cloudy, "28°", "9:00"),
            TodayWeather(R.drawable.preview_cloudy, "33°", "12:00"),
            TodayWeather(R.drawable.preview_cloudy, "36°", "15:00"),
            TodayWeather(R.drawable.preview_cloudy, "31°", "18:00"),
            TodayWeather(R.drawable.preview_cloudy, "26°", "21:00"),
        )
    }
}