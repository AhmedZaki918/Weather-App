package com.weatherapp.data.model.pollution

data class AirPollutionResponse(
    val coord: Coord?,
    val list: List<ListItem>?
)