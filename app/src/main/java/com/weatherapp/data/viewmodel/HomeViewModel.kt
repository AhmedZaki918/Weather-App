package com.weatherapp.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.RequestState
import com.weatherapp.data.model.CurrentWeatherResponse
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

    val requestState: MutableState<RequestState> =
        mutableStateOf(RequestState.LOADING)

    fun initCurrentWeather() {
        requestState.value = RequestState.LOADING
        viewModelScope.launch {
            _currentWeather.value = repo.getCurrentWeather()
            requestState.value = RequestState.COMPLETE
        }
    }
}