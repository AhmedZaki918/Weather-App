package com.weatherapp.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.RequestState
import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.data.network.Resource
import com.weatherapp.data.repository.HomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepo
) : ViewModel() {

    private val _currentWeather = MutableStateFlow<Resource<CurrentWeatherResponse>>(Resource.Idle)
    val currentWeather: StateFlow<Resource<CurrentWeatherResponse>> = _currentWeather

    private val _weatherForecast = MutableStateFlow<Resource<FiveDaysForecastResponse>>(Resource.Idle)
    val weatherForecast: StateFlow<Resource<FiveDaysForecastResponse>> = _weatherForecast

    val requestState: MutableState<RequestState> =
        mutableStateOf(RequestState.LOADING)


    init {
        initCurrentWeather()
        initFiveDaysForecast()
    }

     private fun initCurrentWeather() {
        viewModelScope.launch {
            _currentWeather.value = repo.getCurrentWeather()
            requestState.value = RequestState.COMPLETE
        }
    }

     private fun initFiveDaysForecast() {
         viewModelScope.launch {
            _weatherForecast.value = repo.getFiveDaysForecast()
            requestState.value = RequestState.COMPLETE
        }
    }
}