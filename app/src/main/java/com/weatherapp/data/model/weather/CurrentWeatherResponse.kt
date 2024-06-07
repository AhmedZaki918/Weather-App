package com.weatherapp.data.model.weather

data class CurrentWeatherResponse(
    val visibility: Int? = 0,
    val timezone: Int? = 0,
    val main: Main? = Main(),
    val clouds: Clouds? = Clouds(),
    val sys: Sys? = Sys(),
    val dt: Int? = 0,
    val coord: Coord? = Coord(),
    val weather: List<WeatherItem>? = listOf(),
    val name: String? = "",
    val cod: Int? = 0,
    val id: Int? = 0,
    val base: String? = "",
    val wind: Wind? = Wind()
)