package com.weatherapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.local.Constants.CITY_NAME
import com.weatherapp.data.repository.CityRepo
import com.weatherapp.util.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val repo: CityRepo,
    private val dataStore: DataStoreRepo
) : ViewModel() {

    fun getAllCities() =
        repo.getCities()


    fun saveCity(city: String) {
        viewModelScope.launch {
            dataStore.writeState(CITY_NAME,city)
        }
    }
}