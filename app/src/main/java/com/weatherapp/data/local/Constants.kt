package com.weatherapp.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val BASE_URL = "http://api.openweathermap.org/"
    const val API_KEY = "8659513ac2ead6fa172a8d5d75a4f972"

    const val DEFAULT_CITY = "Cairo"
    const val CITY_SCREEN = "city"
    const val FORMAT_TYPE = "dd.MM.yyyy"
    const val ERROR_SCREEN = "error"

    // Units
    const val DEFAULT_UNIT = "Celsius (°C)"
    const val FAHRENHEIT = "Fahrenheit (°F)"
    const val C_UNIT = "°C"
    const val F_UNIT = "°F"
    const val METRIC = "metric"
    const val IMPERIAL = "imperial"


    // Arguments for navigation
    const val TITLE = "title"
    const val TEXT = "text"
    const val CONFIRM = "confirm"

    // Bottom Sheet types on setting screen
    const val TEMP = "temperature"
    const val LANG = "language"

    // DataStore
    const val PREFERENCE_NAME = "weather_preferences"
    val CITY_NAME = stringPreferencesKey(name = "city_name")
    val TEMP_UNIT = stringPreferencesKey(name = "temp_unit")
}