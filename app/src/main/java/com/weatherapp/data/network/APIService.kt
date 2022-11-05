package com.weatherapp.data.network

import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.geocoding.GeocodingResponse
import com.weatherapp.data.model.pollution.AirPollutionResponse
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("appid") apiKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): CurrentWeatherResponse


    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("appid") apiKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): FiveDaysForecastResponse


    @GET("geo/1.0/direct")
    suspend fun getGeocoding(
        @Query("appid") apiKey: String,
        @Query("q") city: String
    ): List<GeocodingResponse>


    @GET("data/2.5/air_pollution")
    suspend fun getCurrentAirPollution(
        @Query("appid") apiKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): AirPollutionResponse
}
