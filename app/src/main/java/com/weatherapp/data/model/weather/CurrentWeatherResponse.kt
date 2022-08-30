package com.weatherapp.data.model.weather

data class CurrentWeatherResponse(
    val visibility: Int? = 0,
    val timezone: Int? = 0,
    val main: Main?,
    val clouds: Clouds?,
    val sys: Sys?,
    val dt: Int? = 0,
    val coord: Coord?,
    val weather: List<WeatherItem>?,
    val name: String? = "",
    val cod: Int? = 0,
    val id: Int? = 0,
    val base: String? = "",
    val wind: Wind?
)