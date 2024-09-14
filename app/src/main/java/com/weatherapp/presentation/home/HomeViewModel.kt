package com.weatherapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.local.Constants.CITY_NAME
import com.weatherapp.data.local.Constants.DEFAULT_CITY
import com.weatherapp.data.local.Constants.IMPERIAL
import com.weatherapp.data.local.Constants.METRIC
import com.weatherapp.data.local.Constants.TEMP_UNIT
import com.weatherapp.data.network.Resource
import com.weatherapp.data.repository.HomeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import com.weatherapp.util.DataStoreRepo
import com.weatherapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepo,
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // Read temperature unit from user preferences
            val temp = async {
                dataStoreRepo.readInt(TEMP_UNIT).collectLatest { unit ->
                    _uiState.update {
                        it.copy(
                            tempUnit = when (unit) {
                                0 -> METRIC
                                1 -> IMPERIAL
                                else -> METRIC
                            }
                        )
                    }
                }
            }

            // Read city name from user preferences
            val cityName = async {
                dataStoreRepo.readString(CITY_NAME).collectLatest {city ->
                    _uiState.update {
                        it.copy(cityState = city.ifEmpty { DEFAULT_CITY })
                    }
                    initGeocoding()
                }
            }
            temp.await()
            cityName.await()
        }
    }



    private fun initGeocoding(){
        viewModelScope.launch {
            val geocodingResponse = repo.getGeocoding(uiState.value.cityState)

            if (geocodingResponse is Resource.Success) {
                _uiState.update {
                    it.copy(
                        geocoding = geocodingResponse.data
                    )
                }

                val latitude = geocodingResponse.data[0].lat ?: 0.0
                val longitude = geocodingResponse.data[0].lon ?: 0.0
                initCurrentWeather(latitude, longitude)

            } else if (geocodingResponse is Resource.Failure) {
                _uiState.update {
                    it.copy(
                        currentWeatherState = RequestState.ERROR
                    )
                }
            }
        }
    }


    private fun initCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {

            // Current weather
            val weatherResponse = repo.getCurrentWeather(lat, lon, uiState.value.tempUnit)
            if (weatherResponse is Resource.Success) {
                _uiState.update {
                    it.copy(
                        currentWeather = weatherResponse.data,
                        currentWeatherState = RequestState.SUCCESS
                    )
                }

            } else if (weatherResponse is Resource.Failure) {
                _uiState.update {
                    it.copy(
                        currentWeatherState = RequestState.ERROR
                    )
                }
            }


            // Forecast
            val forecastResponse = repo.getFiveDaysForecast(lat, lon, uiState.value.tempUnit)
            if (forecastResponse is Resource.Success) {
                _uiState.update {
                    it.copy(
                        fiveDaysForecast = forecastResponse.data,
                        forecastState = RequestState.SUCCESS
                    )
                }


            } else if (forecastResponse is Resource.Failure) {
                _uiState.update {
                    it.copy(
                        forecastState = RequestState.ERROR
                    )
                }
            }
        }
    }
}