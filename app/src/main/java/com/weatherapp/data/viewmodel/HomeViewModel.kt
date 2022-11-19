package com.weatherapp.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.local.Constants.CITY_NAME
import com.weatherapp.data.local.Constants.DEFAULT_CITY
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
import kotlinx.coroutines.async
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

    var tempUnit = ""
    val requestState: MutableState<RequestState> = mutableStateOf(RequestState.IDLE)
    private val cityState: MutableState<String> = mutableStateOf("")

    private val _geocoding = MutableStateFlow<Resource<List<GeocodingResponse>>>(Resource.Idle)
    val geocoding: StateFlow<Resource<List<GeocodingResponse>>> = _geocoding

    private val _currentWeather = MutableStateFlow<Resource<CurrentWeatherResponse>>(Resource.Idle)
    val currentWeather: StateFlow<Resource<CurrentWeatherResponse>> = _currentWeather

    private val _weatherForecast =
        MutableStateFlow<Resource<FiveDaysForecastResponse>>(Resource.Idle)
    val weatherForecast: StateFlow<Resource<FiveDaysForecastResponse>> = _weatherForecast


    init {
        viewModelScope.launch {
            // Read temperature unit from user preferences
            val temp = async {
                dataStoreRepo.readInt(TEMP_UNIT).collectLatest { unit ->
                    tempUnit = when (unit) {
                        0 -> METRIC
                        1 -> IMPERIAL
                        else -> METRIC
                    }
                }
            }

            // Read city name from user preferences
            val cityName = async {
                dataStoreRepo.readString(CITY_NAME).collectLatest {
                    cityState.value = it.ifEmpty { DEFAULT_CITY }
                    // create geocoding api request from repo
                    _geocoding.value = repo.getGeocoding(cityState.value)
                    requestState.value = RequestState.COMPLETE
                }
            }
            temp.await()
            cityName.await()
        }
    }

    fun initCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            val weather = async { repo.getCurrentWeather(lat, lon, tempUnit) }
            val forecast = async { repo.getFiveDaysForecast(lat, lon, tempUnit) }
            updateUi(weather.await(), forecast.await())
        }
    }

    private fun updateUi(
        weather: Resource<CurrentWeatherResponse>,
        forecast: Resource<FiveDaysForecastResponse>
    ) {
        _currentWeather.value = weather
        _weatherForecast.value = forecast
        requestState.value = RequestState.COMPLETE
    }
}