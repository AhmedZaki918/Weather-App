package com.weatherapp.data.model.geocoding

import com.squareup.moshi.Json

data class GeocodingResponse(
    @Json(name = "local_names") val localNames: LocalNames? = LocalNames(),
    val country: String? = "",
    val name: String? = "",
    val lon: Double? = 0.0,
    val state: String? = "",
    val lat: Double? = 0.0
)