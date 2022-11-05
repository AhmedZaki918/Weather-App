package com.weatherapp.data.model.pollution

data class ListItem(
    val dt: Int? = 0,
    val components: Components?,
    val main: Main?
)