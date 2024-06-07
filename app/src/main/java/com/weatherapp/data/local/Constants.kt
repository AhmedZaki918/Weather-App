package com.weatherapp.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val API_KEY = "8659513ac2ead6fa172a8d5d75a4f972"
    const val BASE_URL = "http://api.openweathermap.org/"

    // Image urls
    const val IMAGE_URL = "http://openweathermap.org/img/wn/"
    const val SIZE = "@2x.png"
    const val CLOUD = "04n"

    const val DEFAULT_CITY = "Cairo"
    const val CITY_SCREEN = "city"
    const val DETAILS_SCREEN = "details"
    const val FORMAT_TYPE = "dd.MM.yyyy"
    const val ERROR_SCREEN = "error"

    // Units
    const val C_UNIT = "°C"
    const val F_UNIT = "°F"
    const val METRIC = "metric"
    const val IMPERIAL = "imperial"
    const val DEGREE = "°"

    // Navigation args
    const val TITLE = "title"
    const val TEXT = "text"
    const val CONFIRM = "confirm"

    // Navigation routes
    const val HOME = "home"
    const val SEARCH = "search"
    const val WISHLIST = "wishlist"
    const val SETTINGS = "settings"

    // DataStore
    const val PREFERENCE_NAME = "weather_preferences"
    val CITY_INDEX = intPreferencesKey(name = "city_index")
    val CITY_NAME = stringPreferencesKey(name = "city_name")
    val TEMP_UNIT = intPreferencesKey(name = "temp_unit")
    val DARK_THEME = booleanPreferencesKey(name = "dark_theme")

    const val EN = "en"
    const val AR = "ar"

    const val LONGITUDE = "longitude"
    const val LATITUDE = "latitude"
    const val CLOUDINESS = "cloudiness percent"

    const val UG_M3 = "ug/m3"
    const val TWELVE_PM = "12:00"
}