package com.weatherapp.data.model.forecast

data class FiveDaysForecastResponse(
    val city: City?,
    val cnt: Int? = 0,
    val cod: String? = "",
    val message: Int? = 0,
    val list: List<ListItem>?
)