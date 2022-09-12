package com.weatherapp.data.model.geocoding

data class GeocodingResponse(
    val local_names: LocalNames?,
    val country: String? = "",
    val name: String? = "",
    val lon: Double? = 0.0,
    val state: String? = "",
    val lat: Double? = 0.0
)