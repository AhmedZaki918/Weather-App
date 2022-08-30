package com.weatherapp.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.RequestState
import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.data.network.Resource
import com.weatherapp.data.repository.HomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepo
) : ViewModel() {

    private val _currentWeather = MutableLiveData<Resource<CurrentWeatherResponse>>()
    val currentWeather: LiveData<Resource<CurrentWeatherResponse>> = _currentWeather

    private val _weatherForecast = MutableLiveData<Resource<FiveDaysForecastResponse>>()
    val weatherForecast: LiveData<Resource<FiveDaysForecastResponse>> = _weatherForecast

    val requestState: MutableState<RequestState> =
        mutableStateOf(RequestState.LOADING)


    fun initCurrentWeather() {
        viewModelScope.launch {
            _currentWeather.value = repo.getCurrentWeather()
            requestState.value = RequestState.COMPLETE
        }
    }

    fun initFiveDaysForecast() {
        viewModelScope.launch {
            _weatherForecast.value = repo.getFiveDaysForecast()
            requestState.value = RequestState.COMPLETE
        }
    }
}