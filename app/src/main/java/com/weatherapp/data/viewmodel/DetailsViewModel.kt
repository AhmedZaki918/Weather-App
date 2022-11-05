package com.weatherapp.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.local.Constants.IMPERIAL
import com.weatherapp.data.local.Constants.METRIC
import com.weatherapp.data.local.Constants.TEMP_UNIT
import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.pollution.AirPollutionResponse
import com.weatherapp.data.network.Resource
import com.weatherapp.data.repository.DetailsRepo
import com.weatherapp.util.DataStoreRepo
import com.weatherapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repo: DetailsRepo,
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    private var tempUnit = ""

    val requestState: MutableState<RequestState> =
        mutableStateOf(RequestState.IDLE)

    private val _currentPollution = MutableStateFlow<Resource<AirPollutionResponse>>(Resource.Idle)
    val currentPollution: StateFlow<Resource<AirPollutionResponse>> = _currentPollution

    private val _weatherForecast =
        MutableStateFlow<Resource<FiveDaysForecastResponse>>(Resource.Idle)
    val weatherForecast: StateFlow<Resource<FiveDaysForecastResponse>> = _weatherForecast

    fun readTempUnit(): String {
        viewModelScope.launch {
            dataStoreRepo.readInt(TEMP_UNIT).collectLatest { unit ->
                tempUnit = when (unit) {
                    0 -> METRIC
                    1 -> IMPERIAL
                    else -> METRIC
                }
            }
        }
        return tempUnit
    }

    fun initGetAirPollution(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _currentPollution.value = repo.getAirPollution(latitude, longitude)
            requestState.value = RequestState.COMPLETE
        }
    }

    fun initFiveDaysForecast(lat: Double, lon: Double, tempUnit: String) {
        viewModelScope.launch {
            _weatherForecast.value = repo.getFiveDaysForecast(lat, lon, tempUnit)
            requestState.value = RequestState.COMPLETE
        }
    }

    fun getAirQuality(value: Int) = repo.airQualityStatus(value)

    // Get all pollutants concentrations in air quality in ug/m3
    fun getNo2Details(value: Double?) = repo.nitrogenDioxide(value)
    fun getPm25Details(value: Double?) = repo.particulates25(value)
    fun getPm10Details(value: Double?) = repo.particulates10(value)
    fun getOzoneDetails(value: Double?) = repo.ozone(value)
}