package com.weatherapp.presentation.home

import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.geocoding.GeocodingResponse
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.util.RequestState

data class HomeUiState(
    val geocoding: List<GeocodingResponse> = listOf(),
    val currentWeather: CurrentWeatherResponse = CurrentWeatherResponse(),
    val fiveDaysForecast: FiveDaysForecastResponse = FiveDaysForecastResponse(),
    val currentWeatherState: RequestState = RequestState.IDLE,
    val forecastState: RequestState = RequestState.IDLE,
    val cityState : String = "",
    val tempUnit : String = ""
)
