package com.weatherapp.presentation.search

import androidx.lifecycle.viewModelScope
import com.weatherapp.data.local.Constants.IMPERIAL
import com.weatherapp.data.local.Constants.METRIC
import com.weatherapp.data.local.Constants.TEMP_UNIT
import com.weatherapp.data.network.Resource
import com.weatherapp.data.repository.SearchRepo
import com.weatherapp.util.BaseViewModel
import com.weatherapp.util.DataStoreRepo
import com.weatherapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepo,
    private val dataStoreRepo: DataStoreRepo
) : BaseViewModel<SearchIntent>() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    override fun onIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.Search -> {
                initGeocoding(intent.keyword)
            }

            is SearchIntent.UpdateSearchKeyword -> {
                _uiState.update {
                    it.copy(
                        searchKeyword = intent.keyword
                    )
                }
            }
        }
    }


    init {
        viewModelScope.launch {
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
    }


    private fun initGeocoding(city: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    weatherState = RequestState.LOADING
                )
            }
            val geocodingResponse = repo.getGeocoding(city)

            if (geocodingResponse is Resource.Success) {
                if (geocodingResponse.data.isNotEmpty()) {
                    _uiState.update {
                        it.copy(geocoding = geocodingResponse.data)
                    }
                    initCurrentWeather()
                    initForecast()
                } else {
                    _uiState.update {
                        it.copy(
                            weatherState = RequestState.ERROR
                        )
                    }
                }

            } else if (geocodingResponse is Resource.Failure) {

                _uiState.update {
                    it.copy(
                        weatherState = RequestState.ERROR
                    )
                }
            }
        }
    }


    private fun initCurrentWeather() {
        viewModelScope.launch {
            val geocoding = uiState.value.geocoding
            if (geocoding.isNotEmpty()) {
                val weatherResponse = repo.getCurrentWeather(
                    geocoding[0].lat ?: 0.0,
                    geocoding[0].lon ?: 0.0,
                    uiState.value.tempUnit
                )

                if (weatherResponse is Resource.Success) {
                    _uiState.update {
                        it.copy(
                            weatherState = RequestState.SUCCESS,
                            currentWeather = weatherResponse.data,
                            windSpeed = it.currentWeather.wind?.speed?.times(60)?.times(60)
                                ?.div(1000)?.toInt() ?: 0
                        )
                    }


                } else if (weatherResponse is Resource.Failure) {
                    _uiState.update {
                        it.copy(
                            weatherState = RequestState.ERROR
                        )
                    }
                }
            }
        }
    }

    private fun initForecast() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    forecastState = RequestState.LOADING
                )
            }

            val geocoding = uiState.value.geocoding
            if (geocoding.isNotEmpty()) {
                val forecastResponse = repo.getFiveDaysForecast(
                    geocoding[0].lat ?: 0.0,
                    geocoding[0].lon ?: 0.0,
                    uiState.value.tempUnit
                )

                if (forecastResponse is Resource.Success) {
                    _uiState.update {
                        it.copy(
                            forecastState = RequestState.SUCCESS,
                            weatherForecast = forecastResponse.data
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
}