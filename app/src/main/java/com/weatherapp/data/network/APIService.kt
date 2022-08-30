package com.weatherapp.data.network

import com.weatherapp.data.model.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("appid") apiKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String = "en"
        ) : CurrentWeatherResponse
}
