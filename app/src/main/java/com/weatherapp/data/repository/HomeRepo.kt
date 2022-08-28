package com.weatherapp.data.repository

import com.weatherapp.R
import com.weatherapp.data.model.TodayWeather

class HomeRepo {

    fun displayFakeData(): List<TodayWeather> {
        return listOf(
            TodayWeather(R.drawable.preview_cloudy,"20°","Now"),
            TodayWeather(R.drawable.preview_cloudy,"23°","3:00"),
            TodayWeather(R.drawable.preview_cloudy,"25°","6:00"),
            TodayWeather(R.drawable.preview_cloudy,"28°","9:00"),
            TodayWeather(R.drawable.preview_cloudy,"33°","12:00"),
            TodayWeather(R.drawable.preview_cloudy,"36°","15:00"),
            TodayWeather(R.drawable.preview_cloudy,"31°","18:00"),
            TodayWeather(R.drawable.preview_cloudy,"26°","21:00"),
        )
    }
}