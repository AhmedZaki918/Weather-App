package com.weatherapp.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.local.Constants.CITY_NAME
import com.weatherapp.data.local.Constants.DEFAULT_CITY
import com.weatherapp.data.local.Constants.DEFAULT_UNIT
import com.weatherapp.data.local.Constants.FAHRENHEIT
import com.weatherapp.data.local.Constants.IMPERIAL
import com.weatherapp.data.local.Constants.METRIC
import com.weatherapp.data.local.Constants.TEMP_UNIT
import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.geocoding.GeocodingResponse
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.data.network.Resource
import com.weatherapp.data.repository.HomeRepo
import com.weatherapp.util.DataStoreRepo
import com.weatherapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepo,
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    private val cityState: MutableState<String> = mutableStateOf("")
    private val _geocoding = MutableStateFlow<Resource<List<GeocodingResponse>>>(Resource.Idle)
    val geocoding: StateFlow<Resource<List<GeocodingResponse>>> = _geocoding

    private val _currentWeather = MutableStateFlow<Resource<CurrentWeatherResponse>>(Resource.Idle)
    val currentWeather: StateFlow<Resource<CurrentWeatherResponse>> = _currentWeather

    private val _weatherForecast =
        MutableStateFlow<Resource<FiveDaysForecastResponse>>(Resource.Idle)
    val weatherForecast: StateFlow<Resource<FiveDaysForecastResponse>> = _weatherForecast

    val requestState: MutableState<RequestState> =
        mutableStateOf(RequestState.IDLE)

    var tempUnit = ""

    init {
        readTempUnit()
        viewModelScope.launch {
            dataStoreRepo.readState(CITY_NAME).collectLatest {
                cityState.value = it.ifEmpty { DEFAULT_CITY }
                initGeocoding(cityState.value)
            }
        }
    }


    private fun readTempUnit() {
        viewModelScope.launch {
            dataStoreRepo.readState(TEMP_UNIT).collectLatest { unit ->
                tempUnit = when (unit) {
                    DEFAULT_UNIT -> METRIC
                    FAHRENHEIT -> IMPERIAL
                    else -> METRIC
                }
            }
        }
    }


    private fun initGeocoding(city: String) {
        viewModelScope.launch {
            _geocoding.value = repo.getGeocoding(city)
            requestState.value = RequestState.COMPLETE
        }
    }

    fun initCurrentWeather(lat: Double, lon: Double, tempUnit: String) {
        viewModelScope.launch {
            _currentWeather.value = repo.getCurrentWeather(lat, lon, tempUnit)
            requestState.value = RequestState.COMPLETE
        }
    }

    fun initFiveDaysForecast(lat: Double, lon: Double, tempUnit: String) {
        viewModelScope.launch {
            _weatherForecast.value = repo.getFiveDaysForecast(lat, lon, tempUnit)
            requestState.value = RequestState.COMPLETE
        }
    }
}