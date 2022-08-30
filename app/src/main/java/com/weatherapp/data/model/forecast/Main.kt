package com.weatherapp.data.model.forecast

data class Main(
    val temp: Double? = 0.0,
    val grnd_level: Int? = 0,
    val humidity: Int? = 0,
    val pressure: Int? = 0,
    val sea_level: Int? = 0,
    val feels_like: Double? = 0.0,
)