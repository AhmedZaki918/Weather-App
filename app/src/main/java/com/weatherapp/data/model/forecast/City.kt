package com.weatherapp.data.model.forecast

data class City(
    val country: String? = "",
    val coord: Coord?,
    val sunrise: Int? = 0,
    val timezone: Int? = 0,
    val sunset: Int? = 0,
    val name: String? = "",
    val id: Int? = 0,
    val population: Int? = 0
)