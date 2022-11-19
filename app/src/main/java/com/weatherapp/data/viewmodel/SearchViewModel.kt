package com.weatherapp.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.local.Constants.IMPERIAL
import com.weatherapp.data.local.Constants.METRIC
import com.weatherapp.data.local.Constants.TEMP_UNIT
import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.geocoding.GeocodingResponse
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.data.network.Resource
import com.weatherapp.data.repository.SearchRepo
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
class SearchViewModel @Inject constructor(
    private val repo: SearchRepo,
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    private var tempUnit = ""
    val searchTextState: MutableState<String> = mutableStateOf("")
    val requestState: MutableState<RequestState> = mutableStateOf(RequestState.IDLE)

    private val _geocoding = MutableStateFlow<Resource<List<GeocodingResponse>>>(Resource.Idle)
    val geocoding: StateFlow<Resource<List<GeocodingResponse>>> = _geocoding

    private val _currentWeather = MutableStateFlow<Resource<CurrentWeatherResponse>>(Resource.Idle)
    val currentWeather: StateFlow<Resource<CurrentWeatherResponse>> = _currentWeather

    private val _weatherForecast =
        MutableStateFlow<Resource<FiveDaysForecastResponse>>(Resource.Idle)
    val weatherForecast: StateFlow<Resource<FiveDaysForecastResponse>> = _weatherForecast


    init {
        viewModelScope.launch {
            dataStoreRepo.readInt(TEMP_UNIT).collectLatest { unit ->
                tempUnit = when (unit) {
                    0 -> METRIC
                    1 -> IMPERIAL
                    else -> METRIC
                }
            }
        }
    }


    fun initGeocoding(city: String) {
        viewModelScope.launch {
            _geocoding.value = repo.getGeocoding(city)
            requestState.value = RequestState.COMPLETE
        }
    }

    fun initCurrentWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val weather = async {
                repo.getCurrentWeather(latitude, longitude, tempUnit)
            }
            val forecast = async {
                repo.getFiveDaysForecast(latitude, longitude, tempUnit)
            }
            updateUi(weather.await(), forecast.await())
        }
    }

    private fun updateUi(
        weatherResponse: Resource<CurrentWeatherResponse>,
        forecastResponse: Resource<FiveDaysForecastResponse>
    ) {
        _currentWeather.value = weatherResponse
        _weatherForecast.value = forecastResponse
    }
}