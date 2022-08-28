package com.weatherapp.data.repository

import com.weatherapp.R
import com.weatherapp.data.model.TodayWeather

class HomeRepo {

    fun displayFakeData(): List<TodayWeather> {
        return listOf(
            TodayWeather(R.drawable.preview_cloudy,"20°","00:00"),
            TodayWeather(R.drawable.preview_cloudy,"23°","03:00"),
            TodayWeather(R.drawable.preview_cloudy,"25°","06:00"),
            TodayWeather(R.drawable.preview_cloudy,"28°","09:00"),
            TodayWeather(R.drawable.preview_cloudy,"33°","12:00"),
            TodayWeather(R.drawable.preview_cloudy,"36°","15:00"),
            TodayWeather(R.drawable.preview_cloudy,"31°","18:00"),
            TodayWeather(R.drawable.preview_cloudy,"26°","21:00"),
        )
    }
}