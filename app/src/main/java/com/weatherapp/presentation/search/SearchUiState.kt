package com.weatherapp.presentation.search

import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.geocoding.GeocodingResponse
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.util.RequestState

data class SearchUiState(
    val tempUnit: String = "",
    val searchKeyword: String = "",
    val windSpeed : Int = 0,
    val geocoding: List<GeocodingResponse> = listOf(),
    val currentWeather: CurrentWeatherResponse = CurrentWeatherResponse(),
    val weatherForecast: FiveDaysForecastResponse = FiveDaysForecastResponse(),
    val weatherState: RequestState = RequestState.IDLE,
    val forecastState: RequestState = RequestState.IDLE
)
