package com.weatherapp.data.repository

import android.content.Context
import com.weatherapp.R
import com.weatherapp.data.local.Constants.API_KEY
import com.weatherapp.data.model.AirQuality
import com.weatherapp.data.model.status.PollutionStatus
import com.weatherapp.data.network.APIService
import com.weatherapp.data.network.SafeApiCall
import com.weatherapp.ui.theme.*
import com.weatherapp.util.getLocal
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DetailsRepo @Inject constructor(
    val api: APIService,
    @ApplicationContext val context: Context
) : SafeApiCall {

    suspend fun getAirPollution(latitude: Double, longitude: Double) = safeApiCall {
        api.getCurrentAirPollution(API_KEY, latitude, longitude)
    }

    suspend fun getFiveDaysForecast(
        lat: Double,
        lon: Double,
        tempUnit: String
    ) = safeApiCall {
        api.getWeatherForecast(
            API_KEY,
            lat,
            lon,
            40,
            tempUnit,
            getLocal()
        )
    }

    // Return the current air quality status
    fun airQualityStatus(aqi: Int): AirQuality {
        context.apply {
            return when (aqi) {
                1 -> AirQuality(0.2f, AQI_Green, getString(R.string.good))
                2 -> AirQuality(0.4f, AQI_Yellow, getString(R.string.fair))
                3 -> AirQuality(0.6f, AQI_Orange, getString(R.string.moderate))
                4 -> AirQuality(0.8f, AQI_Red, getString(R.string.poor))
                else -> AirQuality(1f, AQI_RedDark, getString(R.string.very_poor))
            }
        }
    }

    // Return the status of Nitrogen Dioxide (NO2) based on current concentration in ug/m3
    fun nitrogenDioxide(value: Double?): PollutionStatus {
        context.apply {
            return when (value?.toInt()) {
                in 0..50 -> PollutionStatus(getString(R.string.good), AQI_Green)
                in 50..100 -> PollutionStatus(getString(R.string.fair), AQI_Yellow)
                in 100..200 -> PollutionStatus(getString(R.string.moderate), AQI_Orange)
                in 200..400 -> PollutionStatus(getString(R.string.poor), AQI_Red)
                else -> PollutionStatus(getString(R.string.very_poor), AQI_RedDark)
            }
        }
    }

    // Return the status of Particulates (PM 25) based on current concentration in ug/m3
    fun particulates25(value: Double?): PollutionStatus {
        context.apply {
            return when (value?.toInt()) {
                in 0..15 -> PollutionStatus(getString(R.string.good), AQI_Green)
                in 15..30 -> PollutionStatus(getString(R.string.fair), AQI_Yellow)
                in 30..55 -> PollutionStatus(getString(R.string.moderate), AQI_Orange)
                in 55..110 -> PollutionStatus(getString(R.string.poor), AQI_Red)
                else -> PollutionStatus(getString(R.string.very_poor), AQI_RedDark)
            }
        }
    }

    // Return the status of Ozone (O3) based on current concentration in ug/m3
    fun ozone(value: Double?): PollutionStatus {
        context.apply {
            return when (value?.toInt()) {
                in 0..60 -> PollutionStatus(getString(R.string.good), AQI_Green)
                in 60..120 -> PollutionStatus(getString(R.string.fair), AQI_Yellow)
                in 120..180 -> PollutionStatus(getString(R.string.moderate), AQI_Orange)
                in 180..240 -> PollutionStatus(getString(R.string.poor), AQI_Red)
                else -> PollutionStatus(getString(R.string.very_poor), AQI_RedDark)
            }
        }
    }

    // Return the status of Particulates (PM 10) based on current concentration in ug/m3
    fun particulates10(value: Double?): PollutionStatus {
        context.apply {
            return when (value?.toInt()) {
                in 0..60 -> PollutionStatus(getString(R.string.good), AQI_Green)
                in 60..120 -> PollutionStatus(getString(R.string.fair), AQI_Yellow)
                in 120..180 -> PollutionStatus(getString(R.string.moderate), AQI_Orange)
                in 180..240 -> PollutionStatus(getString(R.string.poor), AQI_Red)
                else -> PollutionStatus(getString(R.string.very_poor), AQI_RedDark)
            }
        }
    }
}