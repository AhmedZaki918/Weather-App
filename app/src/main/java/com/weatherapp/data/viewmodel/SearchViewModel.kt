package com.weatherapp.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.util.RequestState
import com.weatherapp.data.model.forecast.FiveDaysForecastResponse
import com.weatherapp.data.model.geocoding.GeocodingResponse
import com.weatherapp.data.model.weather.CurrentWeatherResponse
import com.weatherapp.data.network.Resource
import com.weatherapp.data.repository.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepo
) : ViewModel() {

    private val _geocoding = MutableStateFlow<Resource<List<GeocodingResponse>>>(Resource.Idle)
    val geocoding: StateFlow<Resource<List<GeocodingResponse>>> = _geocoding

    private val _currentWeather = MutableStateFlow<Resource<CurrentWeatherResponse>>(Resource.Idle)
    val currentWeather: StateFlow<Resource<CurrentWeatherResponse>> = _currentWeather

    private val _weatherForecast =
        MutableStateFlow<Resource<FiveDaysForecastResponse>>(Resource.Idle)
    val weatherForecast: StateFlow<Resource<FiveDaysForecastResponse>> = _weatherForecast

    val searchTextState: MutableState<String> = mutableStateOf("")

    val requestState: MutableState<RequestState> =
        mutableStateOf(RequestState.IDLE)


    fun initGeocoding(city: String) {
        viewModelScope.launch {
            _geocoding.value = repo.getGeocoding(city)
            requestState.value = RequestState.COMPLETE
        }
    }

     fun initCurrentWeather(lat: Double, lon: Double) {
         viewModelScope.launch {
            _currentWeather.value = repo.getCurrentWeather(lat,lon)
         }
     }

     fun initFiveDaysForecast(lat: Double, lon: Double) {
         viewModelScope.launch {
            _weatherForecast.value = repo.getFiveDaysForecast(lat,lon)
         }
     }
}